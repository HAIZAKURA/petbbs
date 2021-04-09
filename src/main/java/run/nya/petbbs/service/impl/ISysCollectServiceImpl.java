package run.nya.petbbs.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.nya.petbbs.mapper.SysCollectMapper;
import run.nya.petbbs.mapper.SysTagMapper;
import run.nya.petbbs.model.entity.SysCollect;
import run.nya.petbbs.model.entity.SysPostTag;
import run.nya.petbbs.model.entity.SysTag;
import run.nya.petbbs.model.vo.CollectVO;
import run.nya.petbbs.service.ISysCollectService;
import run.nya.petbbs.service.ISysPostTagService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 收藏接口实现类
 *
 * 2021/03/07
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ISysCollectServiceImpl extends ServiceImpl<SysCollectMapper, SysCollect> implements ISysCollectService {

    @Autowired
    private ISysPostTagService iSysPostTagService;

    @Resource
    private SysTagMapper sysTagMapper;

    /**
     * 收藏
     *
     * @param  postId
     * @param  userId
     * @return SysCollect
     */
    @Override
    public SysCollect collect(String postId, String userId) {
        SysCollect sysCollect = SysCollect.builder()
                .postId(postId)
                .userId(userId)
                .createTime(new Date())
                .build();
        baseMapper.insert(sysCollect);
        return sysCollect;
    }

    /**
     * 获取收藏列表
     *
     * @param page
     * @param userId
     * @return
     */
    @Override
    public Page<CollectVO> getCollects(Page<CollectVO> page, String userId) {
        Page<CollectVO> iPage = baseMapper.getCollects(page, userId);
        iPage.getRecords().forEach(post -> {
            List<SysPostTag> postTags = iSysPostTagService.selectByPostId(post.getPostId());
            if (!postTags.isEmpty()) {
                List<String> tagIds = postTags.stream().map(SysPostTag::getTagId).collect(Collectors.toList());
                List<SysTag> tags = sysTagMapper.selectBatchIds(tagIds);
                post.setTags(tags);
            }
        });
        return iPage;
    }

}
