package run.nya.petbbs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import run.nya.petbbs.common.exception.ApiAsserts;
import run.nya.petbbs.mapper.SysNotifyMapper;
import run.nya.petbbs.model.entity.SysNotify;
import run.nya.petbbs.model.entity.SysPost;
import run.nya.petbbs.model.entity.SysVideoPost;
import run.nya.petbbs.service.ISysNotifyService;
import run.nya.petbbs.service.ISysPostService;
import run.nya.petbbs.service.ISysUserService;
import run.nya.petbbs.service.ISysVideoPostService;

import java.util.Date;

/**
 * 通知接口实现类
 *
 * 2021/03/08
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ISysNotifyServiceImpl extends ServiceImpl<SysNotifyMapper, SysNotify> implements ISysNotifyService {

    @Autowired
    private ISysUserService iSysUserService;

    @Autowired
    private ISysPostService iSysPostService;

    @Autowired
    ISysVideoPostService iSysVideoPostService;

    /**
     * 评论通知
     *
     * @param  postId
     * @param  commentId
     * @return SysNotify
     */
    @Override
    public void commentNotify(String postId, String commentId) {
        SysPost sysPost = iSysPostService.getById(postId);
        SysVideoPost sysVideoPost = iSysVideoPostService.getById(postId);
        String userId = "";
        String content = "";
        String remark = "";
        if (!ObjectUtils.isEmpty(sysPost)) {
            userId = sysPost.getUserId();
            content = "有人评论了你的话题 " + sysPost.getTitle() + " 哦！";
            remark = "post." + postId + "|" + "comm." + commentId;
        } else if (!ObjectUtils.isEmpty(sysVideoPost)) {
            userId = sysVideoPost.getUserId();
            content = "有人评论了你的视频 " + sysVideoPost.getTitle() + " 哦！";
            remark = "video." + postId + "|" + "comm." + commentId;
        } else {
            ApiAsserts.fail("操作失败");
        }
        SysNotify notify = SysNotify.builder()
                .userId(userId)
                .content(content)
                .remark(remark)
                .createTime(new Date())
                .build();
        baseMapper.insert(notify);
//        return notify;
    }

    /**
     * 关注通知
     *
     * @param  userId
     * @param  followingId
     * @return SysNotify
     */
    @Override
    public void followNotify(String userId, String followingId) {
        String name = iSysUserService.getById(followingId).getUsername();
        String content = name + " 关注了你哦！";
        String remark = "user." + followingId;
        SysNotify notify = SysNotify.builder()
                .userId(userId)
                .content(content)
                .remark(remark)
                .createTime(new Date())
                .build();
        baseMapper.insert(notify);
//        return notify;
    }

}
