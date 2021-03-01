package run.nya.petbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.nya.petbbs.mapper.SysPostTagMapper;
import run.nya.petbbs.model.entity.SysPostTag;
import run.nya.petbbs.model.entity.SysTag;
import run.nya.petbbs.service.ISysPostTagService;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ISysPostTagServiceImpl extends ServiceImpl<SysPostTagMapper, SysPostTag> implements ISysPostTagService {

    /**
     * 获取Topic Tag 关联记录
     *
     * @param  postId
     * @return List
     */
    @Override
    public List<SysPostTag> selectByPostId(String postId) {
        QueryWrapper<SysPostTag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysPostTag::getPostId, postId);
        return baseMapper.selectList(wrapper);
    }

    /**
     * 获取标签换脸话题ID集合
     *
     * @param  id
     * @return Set
     */
    @Override
    public Set<String> selectPostIdsByTagId(String id) {
        return baseMapper.getPostIdsByTagId(id);
    }

    /**
     * 创建中间关系
     *
     * @param id
     * @param tags
     */
    @Override
    public void createPostTag(String id, List<SysTag> tags) {
        baseMapper.delete(new LambdaQueryWrapper<SysPostTag>().eq(SysPostTag::getPostId, id));
        tags.forEach(tag -> {
            SysPostTag sysPostTag = new SysPostTag();
            sysPostTag.setPostId(id);
            sysPostTag.setTagId(tag.getId());
            baseMapper.insert(sysPostTag);
        });
    }

}
