package run.nya.petbbs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import run.nya.petbbs.common.api.ApiResult;
import run.nya.petbbs.model.entity.SysFollow;
import run.nya.petbbs.model.entity.SysUser;
import run.nya.petbbs.model.vo.FollowVO;
import run.nya.petbbs.service.ISysFollowService;
import run.nya.petbbs.service.ISysUserService;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * 用户关注控制器
 *
 * 2021/03/07
 */
@RestController
@RequestMapping("/api")
@Api(tags = {"SysFollowController"})
public class SysFollowController extends BaseController {

    @Resource
    private ISysFollowService iSysFollowService;

    @Resource
    private ISysUserService iSysUserService;

    /**
     * 获取自己粉丝列表
     * 登录用户
     *
     * @param  pageNum
     * @param  pageSize
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "获取自己粉丝列表")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/followers", method = RequestMethod.GET)
    public ApiResult<Page<FollowVO>> getFansList(
            @ApiParam(name = "pageNum", value = "页码:默认1", required = true) @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(name = "pageSize", value = "每页数据量:默认10", required = true) @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            Principal principal
    ) {
        SysUser user = iSysUserService.getUserByUsername(principal.getName());
        Page<FollowVO> page = iSysFollowService.getFans(new Page<>(pageNum, pageSize), user.getId());
        return ApiResult.success(page);
    }

    /**
     * 获取自己关注列表
     * 登录用户
     *
     * @param  pageNum
     * @param  pageSize
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "获取自己关注列表")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/following", method = RequestMethod.GET)
    public ApiResult<Page<FollowVO>> getFollowList(
            @ApiParam(name = "pageNum", value = "页码:默认1", required = true) @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(name = "pageSize", value = "每页数据量:默认10", required = true) @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            Principal principal
    ) {
        SysUser user = iSysUserService.getUserByUsername(principal.getName());
        Page<FollowVO> page = iSysFollowService.getFollow(new Page<>(pageNum, pageSize), user.getId());
        return ApiResult.success(page);
    }

    /**
     * 获取他人粉丝列表
     * 登录用户
     *
     * @param  pageNum
     * @param  pageSize
     * @param  id
     * @return ApiResult
     */
    @ApiOperation(value = "获取他人粉丝列表")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/followers/{id}", method = RequestMethod.GET)
    public ApiResult<Page<FollowVO>> getOtherFansList(
            @ApiParam(name = "pageNum", value = "页码:默认1", required = true) @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(name = "pageSize", value = "每页数据量:默认10", required = true) @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @PathVariable("id") String id
    ) {
        SysUser user = iSysUserService.getById(id);
        Page<FollowVO> page = iSysFollowService.getFans(new Page<>(pageNum, pageSize), user.getId());
        return ApiResult.success(page);
    }

    /**
     * 获取他人关注列表
     * 登录用户
     *
     * @param  pageNum
     * @param  pageSize
     * @param  id
     * @return ApiResult
     */
    @ApiOperation(value = "获取他人关注列表")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/following/{id}", method = RequestMethod.GET)
    public ApiResult<Page<FollowVO>> getOtherFollowList(
            @ApiParam(name = "pageNum", value = "页码:默认1", required = true) @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(name = "pageSize", value = "每页数据量:默认10", required = true) @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @PathVariable("id") String id
    ) {
        SysUser user = iSysUserService.getById(id);
        Page<FollowVO> page = iSysFollowService.getFollow(new Page<>(pageNum, pageSize), user.getId());
        return ApiResult.success(page);
    }

    /**
     * 关注别人
     * 登录用户
     *
     * @param  followId
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "关注别人")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/follow/{followId}", method = RequestMethod.PUT)
    public ApiResult<SysFollow> follow(@PathVariable("followId") String followId, Principal principal) {
        SysUser user = iSysUserService.getUserByUsername(principal.getName());
        if (!user.getActive()) {
            return ApiResult.failed("账号未激活");
        }
        SysFollow sysFollow = iSysFollowService.follow(user.getId(), followId);
        return ApiResult.success(sysFollow);
    }

    /**
     * 取关别人
     * 登录用户
     *
     * @param  followId
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "取关别人")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/follow/{followId}", method = RequestMethod.DELETE)
    public ApiResult<String> unFollow(@PathVariable("followId") String followId, Principal principal) {
        SysUser user = iSysUserService.getUserByUsername(principal.getName());
        if (!user.getActive()) {
            return ApiResult.failed("账号未激活");
        }
        LambdaQueryWrapper<SysFollow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFollow::getUserId, user.getId()).eq(SysFollow::getFollowId, followId);
        iSysFollowService.remove(wrapper);
        return ApiResult.success("取关成功");
    }

}
