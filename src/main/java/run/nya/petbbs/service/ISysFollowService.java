package run.nya.petbbs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import run.nya.petbbs.model.entity.SysFollow;
import run.nya.petbbs.model.vo.FollowVO;

/**
 * 用户关注接口
 *
 * 2021/03/07
 */
public interface ISysFollowService extends IService<SysFollow> {

    /**
     * 获取粉丝列表
     *
     * @param  page
     * @param  id
     * @return Page
     */
    Page<FollowVO> getFans(Page<FollowVO> page, String id);

    /**
     * 获取关注列表
     *
     * @param  page
     * @param  id
     * @return Page
     */
    Page<FollowVO> getFollow(Page<FollowVO> page, String id);

    /**
     * 关注别人
     *
     * @param  userId
     * @param  followId
     * @return SysFollow
     */
    SysFollow follow(String userId, String followId);

}
