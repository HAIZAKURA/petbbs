package run.nya.petbbs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import run.nya.petbbs.common.api.ApiResult;
import run.nya.petbbs.model.dto.CreateCommentDTO;
import run.nya.petbbs.model.entity.SysComment;
import run.nya.petbbs.model.entity.SysUser;
import run.nya.petbbs.model.vo.CommentVO;
import run.nya.petbbs.service.*;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * 评论控制器
 *
 * 2021/03/05
 */
@RestController
@RequestMapping("/api")
@Api(tags = {"SysCommentController"})
public class SysCommentController extends BaseController {

    @Resource
    private ISysCommentService iSysCommentService;

    @Resource
    private ISysUserService iSysUserService;

    @Resource
    private ISysNotifyService iSysNotifyService;

    /**
     * 获取评论列表
     *
     * @param  postId
     * @param  pageNum
     * @param  pageSize
     * @return ApiResult
     */
    @ApiOperation(value = "获取评论列表")
    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    public ApiResult<Page<CommentVO>> getCommentList(
            @ApiParam(name = "postId", value = "标签ID", required = true) @RequestParam(value = "postId") String postId,
            @ApiParam(name = "pageNum", value = "页码:默认1", required = true) @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(name = "pageSize", value = "每页数据量:默认10", required = true) @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize

    ) {
        Page<CommentVO> page = iSysCommentService.getList(new Page<>(pageNum, pageSize), postId);
        return ApiResult.success(page);
    }

    /**
     * 创建评论
     * 登录用户
     *
     * @param  dto
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "创建评论")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public ApiResult<SysComment> create(@RequestBody CreateCommentDTO dto, Principal principal) {
        SysUser user = iSysUserService.getUserByUsername(principal.getName());
        if (!user.getActive()) {
            return ApiResult.failed("账号未激活");
        }
        SysComment sysComment = iSysCommentService.create(dto, user);
        iSysNotifyService.commentNotify(dto.getPostId(), sysComment.getId());
        return ApiResult.success(sysComment);
    }

    /**
     * 删除评论
     * 登录用户
     *
     * @param  id
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "删除评论")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.DELETE)
    public ApiResult<String> delete(
            @ApiParam(name = "id", value = "话题ID", required = true) @PathVariable("id") String id,
            Principal principal
    ) {
        SysUser sysUser = iSysUserService.getUserByUsername(principal.getName());
        SysComment sysComment = iSysCommentService.getById(id);
        if (ObjectUtils.isEmpty(sysComment)) {
            return ApiResult.failed("评论不存在");
        }
        if (!sysComment.getUserId().equals(sysUser.getId())) {
            return ApiResult.failed("不能删除别人的评论");
        }
        iSysCommentService.removeById(id);
        return ApiResult.success("删除成功");
    }

    /**
     * 删除评论
     * 超级管理员
     * 管理员
     *
     * @param  id
     * @return ApiResult
     */
    @ApiOperation(value = "管理员删除评论")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/comment/{id}", method = RequestMethod.DELETE)
    public ApiResult<String> deleteByAdmin(
            @ApiParam(name = "id", value = "话题ID", required = true) @PathVariable("id") String id
    ) {
        SysComment sysComment = iSysCommentService.getById(id);
        if (ObjectUtils.isEmpty(sysComment)) {
            return ApiResult.failed("评论不存在");
        }
        iSysCommentService.removeById(id);
        return ApiResult.success("删除成功");
    }

}
