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
import run.nya.petbbs.model.entity.SysPhoto;
import run.nya.petbbs.model.entity.SysPost;
import run.nya.petbbs.model.entity.SysVideoPost;
import run.nya.petbbs.service.*;

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
    private ISysVideoPostService iSysVideoPostService;

    @Autowired
    private ISysPhotoService iSysPhotoService;

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
//        SysVideoPost sysVideoPost = iSysVideoPostService.getById(postId);
        SysPhoto sysPhoto = iSysPhotoService.getById(postId);
        String userId = "";
        String content = "";
        String remark = "";
        if (!ObjectUtils.isEmpty(sysPost)) {
            userId = sysPost.getUserId();
            content = "有人评论了你的话题 《" + sysPost.getTitle() + "》 哦！";
            remark = "post/" + postId;
        } else if (!ObjectUtils.isEmpty(sysPhoto)) {
            userId = sysPhoto.getUserId();
            content = "有人评论了你的照片哦！";
            remark = "photos/" + postId;
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
     * 引用通知
     *
     * @param userId
     * @param postId
     * @param commentId
     */
    @Override
    public void quoteNotify(String userId, String postId, String commentId) {
        SysPost sysPost = iSysPostService.getById(postId);
//        SysVideoPost sysVideoPost = iSysVideoPostService.getById(postId);
        SysPhoto sysPhoto = iSysPhotoService.getById(postId);
        String content = "";
        String remark = "";
        if (!ObjectUtils.isEmpty(sysPost)) {
            content = "有人回复了你哦！";
            remark = "post/" + postId;
        } else if (!ObjectUtils.isEmpty(sysPhoto)) {
            content = "有人回复了你哦！";
            remark = "photos/" + postId;
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
        String remark = "user/" + followingId;
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
