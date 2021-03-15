package run.nya.petbbs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import run.nya.petbbs.common.api.ApiResult;
import run.nya.petbbs.model.entity.SysCollect;
import run.nya.petbbs.model.entity.SysUser;
import run.nya.petbbs.model.vo.CollectVO;
import run.nya.petbbs.service.ISysCollectService;
import run.nya.petbbs.service.ISysUserService;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * 收藏控制器
 *
 * 2021/03/07
 */
@RestController
@RequestMapping("/api")
@Api(tags = {"SysCollectController"})
public class SysCollectController extends BaseController {

    @Resource
    private ISysCollectService iSysCollectService;

    @Resource
    private ISysUserService iSysUserService;

    /**
     * 获取收藏列表
     * 登录用户
     *
     * @param  pageNum
     * @param  pageSize
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "获取收藏列表")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/collect", method = RequestMethod.GET)
    public ApiResult<Page<CollectVO>> getCollectList(
            @ApiParam(name = "pageNum", value = "页码:默认1", required = true) @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(name = "pageSize", value = "每页数据量:默认10", required = true) @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            Principal principal
    ) {
        SysUser sysUser = iSysUserService.getUserByUsername(principal.getName());
        if (ObjectUtils.isEmpty(sysUser)) {
            return ApiResult.failed("用户不存在");
        }
        Page<CollectVO> page = iSysCollectService.getCollects(new Page<CollectVO>(pageNum, pageSize), sysUser.getId());
        return ApiResult.success(page);
    }

    /**
     * 收藏
     * 登录用户
     *
     * @param  postId
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "收藏")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/collect/{postId}", method = RequestMethod.PUT)
    public ApiResult<SysCollect> collect(@PathVariable("postId") String postId, Principal principal) {
        SysUser user = iSysUserService.getUserByUsername(principal.getName());
        if (!user.getActive()) {
            return ApiResult.failed("账号未激活");
        }
        SysCollect isExist = iSysCollectService.getOne(new LambdaQueryWrapper<SysCollect>().eq(SysCollect::getPostId, postId).eq(SysCollect::getUserId, user.getId()));
        if (!ObjectUtils.isEmpty(isExist)) {
            return ApiResult.failed("已经收藏过啦");
        }
        SysCollect sysCollect = iSysCollectService.collect(postId, user.getId());
        return ApiResult.success(sysCollect);
    }

    /**
     * 取消收藏
     * 登录用户
     *
     * @param  postId
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "取消收藏")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/collect/{postId}", method = RequestMethod.DELETE)
    public ApiResult<String> unCollect(@PathVariable("postId") String postId, Principal principal) {
        SysUser user = iSysUserService.getUserByUsername(principal.getName());
        if (!user.getActive()) {
            return ApiResult.failed("账号未激活");
        }
        LambdaQueryWrapper<SysCollect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysCollect::getPostId, postId).eq(SysCollect::getUserId, user.getId());
        iSysCollectService.remove(wrapper);
        return ApiResult.success("操作成功");
    }

}
