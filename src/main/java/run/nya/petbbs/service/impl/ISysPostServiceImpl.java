package run.nya.petbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import run.nya.petbbs.common.exception.ApiAsserts;
import run.nya.petbbs.config.redis.RedisService;
import run.nya.petbbs.mapper.SysPostMapper;
import run.nya.petbbs.mapper.SysTagMapper;
import run.nya.petbbs.mapper.SysUserMapper;
import run.nya.petbbs.model.dto.CreatePostDTO;
import run.nya.petbbs.model.entity.*;
import run.nya.petbbs.model.vo.PostVO;
import run.nya.petbbs.model.vo.ProfileVO;
import run.nya.petbbs.service.ISysPostService;
import run.nya.petbbs.service.ISysPostTagService;
import run.nya.petbbs.service.ISysTagService;
import run.nya.petbbs.service.ISysUserService;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 话题实现类
 *
 * 2021/03/01
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ISysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements ISysPostService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private ISysUserService iSysUserService;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysTagMapper sysTagMapper;

    @Autowired
    @Lazy
    private ISysTagService iSysTagService;

    @Autowired
    private ISysPostTagService iSysPostTagService;

    /**
     * 获取首页话题列表
     *
     * @param  page
     * @param  tab
     * @return Page
     */
    @Override
    public Page<PostVO> getList(Page<PostVO> page, String tab, Integer sectionId, String tagId) {
        // 查询话题
        Page<PostVO> iPage = baseMapper.selectListAndPage(page, tab, sectionId, tagId);
        // 查询话题标签
        iPage.getRecords().forEach(post -> {
            List<SysPostTag> postTags = iSysPostTagService.selectByPostId(post.getId());
            if (!postTags.isEmpty()) {
                List<String> tagIds = postTags.stream().map(SysPostTag::getTagId).collect(Collectors.toList());
                List<SysTag> tags = sysTagMapper.selectBatchIds(tagIds);
                post.setTags(tags);
            }
        });
        return iPage;
    }


    /**
     * 查看话题详情
     *
     * @param  id
     * @return Map
     */
    @Override
    public Map<String, Object> viewPost(String id) {
        Map<String, Object> map = new HashMap<>(16);
        SysPost post = baseMapper.selectById(id);
        if (ObjectUtils.isEmpty(post)) {
            ApiAsserts.fail("话题不存在");
        }
        // 查询话题
        post.setView(post.getView() + 1);
        baseMapper.updateById(post);
        // emoji
        post.setContent(EmojiParser.parseToUnicode(post.getContent()));
        map.put("post", post);
        QueryWrapper<SysPostTag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysPostTag::getPostId, post.getId());
        Set<String> set = new HashSet<>();
        for (SysPostTag postTag : iSysPostTagService.list(wrapper)) {
            set.add(postTag.getTagId());
        }
        List<SysTag> tags = iSysTagService.listByIds(set);
        // 作者
        map.put("tags", tags);
        ProfileVO user = iSysUserService.getUserProfile(post.getUserId());
        map.put("user", user);
        return map;
    }

    /**
     * 查询当前作者其他话题
     * 10篇
     *
     * @param  userId
     * @param  postId
     * @return List
     */
    @Override
    public List<SysPost> selectAuthorOtherPost(String userId, String postId) {
        List<SysPost> posts = (List<SysPost>) redisService.get("otherPosts");
        if (ObjectUtils.isEmpty(posts)) {
            QueryWrapper<SysPost> wrapper = new QueryWrapper<>();
            wrapper.lambda().eq(SysPost::getUserId, userId).orderByDesc(SysPost::getCreateTime);
            if (postId != null) {
                wrapper.lambda().ne(SysPost::getId, postId);
            }
            wrapper.last("limit " + 10);
            posts = baseMapper.selectList(wrapper);
            redisService.set("otherPosts", posts, 60 * 60);
        }
        return posts;
    }

    /**
     * 用户主页查询用户的话题
     * 10篇
     *
     * @param  userId
     * @param  page
     * @return Page
     */
    @Override
    public Page<SysPost> selectPostByUserId(Page<SysPost> page, String userId) {
        QueryWrapper<SysPost> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysPost::getUserId, userId);
        Page<SysPost> postPage = baseMapper.selectPage(page, wrapper);
        return postPage;
    }

    /**
     * 获取随机推荐
     * 10篇
     *
     * @param  id
     * @return List
     */
    @Override
    public List<SysPost> getRecommend(String id) {
        return baseMapper.selectRecommend(id);
    }

    /**
     * 创建话题
     *
     * @param  dto
     * @param  sysUser
     * @return SysPost
     */
    @Override
    public SysPost create(CreatePostDTO dto, SysUser sysUser) {
        SysPost postIsExist = baseMapper.selectOne(new LambdaQueryWrapper<SysPost>().eq(SysPost::getTitle, dto.getTitle()));
        if (!ObjectUtils.isEmpty(postIsExist)) {
            ApiAsserts.fail("话题已存在");
        }
        SysPost post = SysPost.builder()
                .userId(sysUser.getId())
                .title(dto.getTitle())
                .content(EmojiParser.parseToUnicode(dto.getContent()))
                .sectionId(dto.getSectionId())
                .createTime(new Date())
                .build();
        baseMapper.insert(post);
        Integer newScore = sysUser.getScore() + 1;
        sysUserMapper.updateById(sysUser.setScore(newScore));
        if (ObjectUtils.isEmpty(dto.getTags())) {
            ApiAsserts.fail("标签不能为空");
        }
        for(SysTag tag : dto.getTags()) {
            SysTag sysTag = iSysTagService.getById(tag.getId());
            sysTag.setPostCount(sysTag.getPostCount() + 1);
            iSysTagService.updateById(sysTag);
        }
        iSysPostTagService.createPostTag(post.getId(), dto.getTags());
//        redisService.del("getPostListAndPage");
        return post;
    }

    /**
     * 关键字检索
     *
     * @param  keyword
     * @param  page
     * @return Page
     */
    @Override
    public Page<PostVO> searchByKey(Page<PostVO> page, String keyword) {
        return baseMapper.searchByKey(page, keyword);
    }

}
