package run.nya.petbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.nya.petbbs.mapper.SysTagMapper;
import run.nya.petbbs.model.entity.SysPost;
import run.nya.petbbs.model.entity.SysTag;
import run.nya.petbbs.model.vo.PostVO;
import run.nya.petbbs.service.ISysPostService;
import run.nya.petbbs.service.ISysPostTagService;
import run.nya.petbbs.service.ISysTagService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ISysTagServiceImpl extends ServiceImpl<SysTagMapper, SysTag> implements ISysTagService {

    @Autowired
    private ISysPostTagService iSysPostTagService;

    @Autowired
    private ISysPostService iSysPostService;

    /**
     * 添加标签
     *
     * @param  tags
     * @return List
     */
    @Override
    public List<SysTag> addTags(List<String> tags) {
        List<SysTag> tagList = new ArrayList<>();
        for (String tag : tags) {
            SysTag sysTag = baseMapper.selectOne(new LambdaQueryWrapper<SysTag>().eq(SysTag::getName, tag));
            if (sysTag == null) {
                sysTag = SysTag.builder().name(tag).build();
                baseMapper.insert(sysTag);
            } else {
                sysTag.setPostCount(sysTag.getPostCount() + 1);
                baseMapper.updateById(sysTag);
            }
            tagList.add(sysTag);
        }
        return tagList;
    }

    /**
     * 获取标签关联话题
     *
     * @param  postPage
     * @param  id
     * @return Page
     */
    @Override
    public Page<SysPost> selectPostsByTagId(Page<SysPost> postPage, String id) {
        Set<String> ids = iSysPostTagService.selectPostIdsByTagId(id);
        LambdaQueryWrapper<SysPost> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysPost::getId, ids);
        return iSysPostService.page(postPage, wrapper);
    }

}
