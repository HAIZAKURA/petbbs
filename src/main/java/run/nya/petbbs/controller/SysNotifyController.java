package run.nya.petbbs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import run.nya.petbbs.common.api.ApiResult;
import run.nya.petbbs.model.entity.SysNotify;
import run.nya.petbbs.model.entity.SysUser;
import run.nya.petbbs.service.ISysNotifyService;
import run.nya.petbbs.service.ISysUserService;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;

/**
 * 通知控制器
 *
 * 2021/03/08
 */
@RestController
@RequestMapping("/api")
@Api(tags = {"SysNotifyController"})
public class SysNotifyController extends BaseController {

    @Resource
    private ISysNotifyService iSysNotifyService;

    @Resource
    private ISysUserService iSysUserService;

    /**
     * 获取所有通知
     * 登录用户
     *
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "获取所有通知")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/notify", method = RequestMethod.GET)
    public ApiResult<List<SysNotify>> getList(Principal principal) {
        SysUser user = iSysUserService.getUserByUsername(principal.getName());
        if (!user.getActive()) {
            return ApiResult.failed("用户未激活");
        }
        LambdaQueryWrapper<SysNotify> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysNotify::getUserId, user.getId());
        List<SysNotify> notifies = iSysNotifyService.list(wrapper);
        return ApiResult.success(notifies);
    }

    /**
     * 获取未读通知
     * 登录用户
     *
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "获取未读通知")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/notify/new", method = RequestMethod.GET)
    public ApiResult<List<SysNotify>> getNewList(Principal principal) {
        SysUser user = iSysUserService.getUserByUsername(principal.getName());
        if (!user.getActive()) {
            return ApiResult.failed("用户未激活");
        }
        LambdaQueryWrapper<SysNotify> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysNotify::getUserId, user.getId()).eq(SysNotify::getState, true);
        List<SysNotify> notifies = iSysNotifyService.list(wrapper);
        return ApiResult.success(notifies);
    }

    /**
     * 已读通知
     * 登录用户
     *
     * @param  id
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "已读通知")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/notify/{id}", method = RequestMethod.PUT)
    public ApiResult<String> setRead(@PathVariable("id") String id, Principal principal) {
        SysUser sysUser = iSysUserService.getUserByUsername(principal.getName());
        if (!sysUser.getActive()) {
            return ApiResult.failed("用户未激活");
        }
        SysNotify sysNotify = iSysNotifyService.getById(id);
        if (ObjectUtils.isEmpty(sysNotify)) {
            return ApiResult.failed("通知不存在");
        }
        if (!sysNotify.getUserId().equals(sysUser.getId())) {
            return ApiResult.failed("操作失败");
        }
        sysNotify.setState(false);
        iSysNotifyService.updateById(sysNotify);
        return ApiResult.success("操作成功");
    }

}
