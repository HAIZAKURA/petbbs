package run.nya.petbbs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import run.nya.petbbs.common.api.ApiResult;
import run.nya.petbbs.model.entity.SysCollect;
import run.nya.petbbs.model.entity.SysUser;
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
