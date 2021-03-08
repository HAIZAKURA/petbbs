package run.nya.petbbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import run.nya.petbbs.model.entity.SysNotify;

/**
 * 通知接口
 *
 * 2021/03/08
 */
public interface ISysNotifyService extends IService<SysNotify> {

    /**
     * 评论通知
     *
     * @param  postId
     * @param  commentId
     * @return SysNotify
     */
    void commentNotify(String postId, String commentId);

    /**
     * 关注通知
     *
     * @param  userId
     * @param  followingId
     * @return SysNotify
     */
    void followNotify(String userId, String followingId);

}
