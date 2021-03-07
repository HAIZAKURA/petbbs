package run.nya.petbbs.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.nya.petbbs.mapper.SysFollowMapper;
import run.nya.petbbs.model.entity.SysFollow;
import run.nya.petbbs.model.vo.FollowVO;
import run.nya.petbbs.service.ISysFollowService;

/**
 * 用户关注接口实现类
 *
 * 2021/03/07
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ISysFollowServiceImpl extends ServiceImpl<SysFollowMapper, SysFollow> implements ISysFollowService {

    /**
     * 获取粉丝列表
     *
     * @param  page
     * @param  id
     * @return
     */
    @Override
    public Page<FollowVO> getFans(Page<FollowVO> page, String id) {
        return baseMapper.getFansPage(page, id);
    }

    /**
     * 获取关注列表
     *
     * @param  page
     * @param  id
     * @return
     */
    @Override
    public Page<FollowVO> getFollow(Page<FollowVO> page, String id) {
        return baseMapper.getFollowPage(page, id);
    }

    /**
     * 关注别人
     *
     * @param  userId
     * @param  followId
     * @return SysFollow
     */
    @Override
    public SysFollow follow(String userId, String followId) {
        SysFollow sysFollow = SysFollow.builder()
                .userId(userId)
                .followId(followId)
                .build();
        baseMapper.insert(sysFollow);
        return sysFollow;
    }

}
