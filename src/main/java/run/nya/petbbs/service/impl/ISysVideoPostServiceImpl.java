package run.nya.petbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import run.nya.petbbs.common.exception.ApiAsserts;
import run.nya.petbbs.mapper.SysTagMapper;
import run.nya.petbbs.mapper.SysUserMapper;
import run.nya.petbbs.mapper.SysVideoPostMapper;
import run.nya.petbbs.model.dto.CreateVideoDTO;
import run.nya.petbbs.model.entity.SysPostTag;
import run.nya.petbbs.model.entity.SysTag;
import run.nya.petbbs.model.entity.SysUser;
import run.nya.petbbs.model.entity.SysVideoPost;
import run.nya.petbbs.model.vo.ProfileVO;
import run.nya.petbbs.model.vo.VideoPostVO;
import run.nya.petbbs.service.ISysPostTagService;
import run.nya.petbbs.service.ISysTagService;
import run.nya.petbbs.service.ISysUserService;
import run.nya.petbbs.service.ISysVideoPostService;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 视频话题实现类
 *
 * 2021/03/07
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ISysVideoPostServiceImpl extends ServiceImpl<SysVideoPostMapper, SysVideoPost> implements ISysVideoPostService {

    @Autowired
    private ISysUserService iSysUserService;

    @Autowired
    private ISysPostTagService iSysPostTagService;

    @Autowired
    @Lazy
    private ISysTagService iSysTagService;

    @Resource
    private SysTagMapper sysTagMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 获取视频话题列表
     *
     * @param  page
     * @param  tab
     * @param  tagId
     * @return Page
     */
    @Override
    public Page<VideoPostVO> getList(Page<VideoPostVO> page, String tab, String tagId) {
        Page<VideoPostVO> iPage = baseMapper.selectListAndPage(page, tab, tagId);
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
     * 查看视频详情
     *
     * @param  id
     * @return Map
     */
    @Override
    public Map<String, Object> viewVideo(String id) {
        Map<String, Object> map = new HashMap<>(16);
        SysVideoPost videoPost = baseMapper.selectById(id);
        if (ObjectUtils.isEmpty(videoPost)) {
            ApiAsserts.fail("视频不存在");
        }
        videoPost.setView(videoPost.getView() + 1);
        baseMapper.updateById(videoPost);
        map.put("video", videoPost);
        QueryWrapper<SysPostTag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysPostTag::getPostId, videoPost.getId());
        Set<String> set = new HashSet<>();
        for (SysPostTag postTag : iSysPostTagService.list(wrapper)) {
            set.add(postTag.getTagId());
        }
        List<SysTag> tags = iSysTagService.listByIds(set);
        map.put("tags", tags);
        ProfileVO user = iSysUserService.getUserProfile(videoPost.getUserId());
        map.put("user", user);
        return map;
    }

    /**
     * 创建视频
     *
     * @param  dto
     * @param  user
     * @return SysVideoPost
     */
//    @Override
//    public SysVideoPost create(CreateVideoDTO dto, SysUser user) {
//        SysVideoPost videoIsExist = baseMapper.selectOne(new LambdaQueryWrapper<SysVideoPost>().eq(SysVideoPost::getTitle, dto.getTitle()));
//        if (!ObjectUtils.isEmpty(videoIsExist)) {
//            ApiAsserts.fail("视频已存在");
//        }
//        SysVideoPost videoPost = SysVideoPost.builder()
//                .title(dto.getTitle())
//                .video(dto.getVideo())
//                .content(dto.getContent())
//                .userId(user.getId())
//                .createTime(new Date())
//                .build();
//        baseMapper.insert(videoPost);
//        Integer newScore = user.getScore() + 1;
//        sysUserMapper.updateById(user.setScore(newScore));
//        if (ObjectUtils.isEmpty(dto.getTags())) {
//            ApiAsserts.fail("标签不能为空");
//        }
//        iSysPostTagService.createPostTag(videoPost.getId(), dto.getTags());
//        return videoPost;
//    }

    /**
     * 关键词检索
     *
     * @param  page
     * @param  keyword
     * @return Page
     */
    @Override
    public Page<VideoPostVO> searchByKey(Page<VideoPostVO> page, String keyword) {
        return baseMapper.searchByKey(page, keyword);
    }

    /**
     * 随机推荐
     * 10篇
     *
     * @param  id
     * @return List
     */
    @Override
    public List<VideoPostVO> getRecommend(String id) {
        return baseMapper.selectRecommend(id);
    }

    /**
     * 用户主页查询用户的视频
     *
     * @param  page
     * @param  userId
     * @return Page
     */
    @Override
    public Page<SysVideoPost> selectPostByUserId(Page<SysVideoPost> page, String userId) {
        QueryWrapper<SysVideoPost> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysVideoPost::getUserId, userId);
        Page<SysVideoPost> postPage = baseMapper.selectPage(page, wrapper);
        return postPage;
    }

}
