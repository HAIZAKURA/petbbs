package run.nya.petbbs.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import run.nya.petbbs.common.api.ApiResult;
import run.nya.petbbs.config.redis.RedisService;
import run.nya.petbbs.model.dto.ActiveDTO;
import run.nya.petbbs.model.dto.LoginDTO;
import run.nya.petbbs.model.dto.RegisterDTO;
import run.nya.petbbs.model.entity.SysUser;
import run.nya.petbbs.service.ISysUserService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 用户控制器
 *
 * 2021/02/22
 */
@RestController
@RequestMapping("/api")
@Api(tags = "SysUserController")
public class SysUserController extends BaseController {

    @Resource
    private ISysUserService iSysUserService;

    @Resource
    private RedisService redisService;

    /**
     * 用户登录控制器
     *
     * @param  dto
     * @return ApiResult
     */
    @ApiOperation(value = "用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResult<Map<String, String>> login(@Valid @RequestBody LoginDTO dto) {
        String token = iSysUserService.executeLogin(dto);
        if (ObjectUtils.isEmpty(token)) {
            return ApiResult.failed("用户名或密码错误");
        }
        Map<String, String> map = new HashMap<>(16);
        map.put("token", token);
        return ApiResult.success(map, "登录成功");
    }

    /**
     * 用户注册控制器
     *
     * @param  dto
     * @return ApiResult
     */
    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiResult<Map<String, Object>> register(@Valid @RequestBody RegisterDTO dto) {
        SysUser sysUser = iSysUserService.executeRegister(dto);
        if (ObjectUtils.isEmpty(sysUser)) {
            return ApiResult.failed("账号注册失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("user", sysUser);
        return ApiResult.success(map);
    }

    /**
     * 用户激活控制器
     *
     * @param  dto
     * @return ApiResult
     */
    @ApiOperation(value = "用户激活")
    @RequestMapping(value = "/active", method = RequestMethod.POST)
    public ApiResult<Object> active(@Valid @RequestBody ActiveDTO dto) {
        SysUser sysUser = iSysUserService.getUserByUsername(dto.getUser());
        if (ObjectUtils.isEmpty(sysUser)) {
            ApiResult.failed("用户不存在");
        }
        String activeCode = (String) redisService.get("activeCode[" + dto.getUser() + "]");
        if (sysUser.getActive() && ObjectUtils.isEmpty(activeCode)) {
            return ApiResult.success(null, "已激活");
        }
        Assert.isTrue(activeCode.equals(dto.getCode()), "激活码错误");
        sysUser.setActive(true);
        if (iSysUserService.updateById(sysUser)) {
            redisService.del("activeCode[" + dto.getUser() + "]");
            return ApiResult.success(null, "激活成功");
        }
        return ApiResult.failed("激活失败");
    }

    /**
     * 获取当前用户信息控制器
     * 登录用户
     *
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "获取当前用户信息")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ApiResult<SysUser> getUser(Principal principal) {
        SysUser sysUser = iSysUserService.getUserByUsername(principal.getName());
        return ApiResult.success(sysUser);
    }

    /**
     * 获取用户信息
     * 超级管理员
     * 管理员
     *
     * @param  username
     * @return ApiResult
     */
    @ApiOperation(value = "管理员获取用户信息")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/user/{username}", method = RequestMethod.GET)
    public ApiResult<SysUser> getUserByAdmin(@PathVariable("username") String username) {
        SysUser sysUser = iSysUserService.getUserByUsername(username);
        return ApiResult.success(sysUser);
    }

    /**
     * 修改当前用户信息控制器
     * 登录用户
     *
     * @param  sysUser
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "修改当前用户信息")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public ApiResult<Object> updateUser(@RequestBody SysUser sysUser, Principal principal) {
        if (Objects.equals(sysUser.getUsername(), iSysUserService.getUserByUsername(principal.getName()).getUsername())) {
            iSysUserService.updateById(sysUser);
            return ApiResult.success(sysUser, "修改成功");
        }
        return ApiResult.failed("修改失败");
    }

    /**
     * 修改用户信息控制器
     * 超级管理员
     * 管理员
     *
     * @param  username
     * @param  sysUser
     * @return ApiResult
     */
    @ApiOperation(value = "管理员修改用户信息")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/user/{username}", method = RequestMethod.PUT)
    public ApiResult<Object> updateUserByAdmin(@PathVariable("username") String username, @RequestBody SysUser sysUser) {
        if (Objects.equals(username, sysUser.getUsername())) {
            iSysUserService.updateById(sysUser);
            return ApiResult.success(sysUser, "修改成功");
        }
        return ApiResult.failed("修改失败");
    }

}
