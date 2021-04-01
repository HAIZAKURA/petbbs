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
import run.nya.petbbs.config.redis.RedisService;
import run.nya.petbbs.model.dto.ActiveDTO;
import run.nya.petbbs.model.dto.LoginDTO;
import run.nya.petbbs.model.dto.RegisterDTO;
import run.nya.petbbs.model.entity.SysPost;
import run.nya.petbbs.model.entity.SysUser;
import run.nya.petbbs.service.ISysPostService;
import run.nya.petbbs.service.ISysUserService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 用户控制器
 *
 * 2021/02/27
 */
@RestController
@RequestMapping("/api")
@Api(tags = "SysUserController")
public class SysUserController extends BaseController {

    @Resource
    private ISysUserService iSysUserService;

    @Resource
    private ISysPostService iSysPostService;

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
            return ApiResult.failed("用户不存在");
        }
        String activeCode = (String) redisService.get("activeCode[" + dto.getUser() + "]");
        if (sysUser.getActive() && ObjectUtils.isEmpty(activeCode)) {
            return ApiResult.success(null, "已激活");
        }
        if (!activeCode.equals(dto.getCode())) {
            return ApiResult.failed("激活码错误");
        }
        sysUser.setActive(true);
        if (iSysUserService.updateById(sysUser)) {
            redisService.del("activeCode[" + dto.getUser() + "]");
            return ApiResult.success(null, "激活成功");
        }
        return ApiResult.failed("激活失败");
    }

    /**
     * 重新发送激活码
     *
     * @param  name
     * @param  email
     * @return ApiResult
     */
    @ApiOperation(value = "重新发送激活码")
    @RequestMapping(value = "/reactive", method = RequestMethod.GET)
    public ApiResult<String> reActive(
            @RequestParam("name") String name,
            @RequestParam("email") String email
    ) {
        SysUser user = iSysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, name).eq(SysUser::getEmail, email));
        if (ObjectUtils.isEmpty(user)) {
            return ApiResult.failed("用户信息错误");
        }
        try {
            iSysUserService.reActive(name, email);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.success("发送失败");
        }
        return ApiResult.success("发送成功");
    }

    /**
     * 获取当前用户信息
     * 登录用户
     *
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "获取当前用户信息")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/user/info", method = RequestMethod.GET)
    public ApiResult<Map<String, Object>> getUserInfo(Principal principal) {
        SysUser sysUser = iSysUserService.getUserByUsername(principal.getName());
        if (ObjectUtils.isEmpty(sysUser)) {
            return ApiResult.failed("用户不存在");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("user", sysUser);
        return ApiResult.success(map);
    }

    /**
     * 获取指定用户信息
     *
     * @param  usernameXid
     * @return ApiResult
     */
    @ApiOperation(value = "获取指定用户信息")
    @RequestMapping(value = "/user/info/{usernameXid}", method = RequestMethod.GET)
    public ApiResult<Map<String, Object>> getUserInfoByUsernameOrId(
            @ApiParam(name = "usernameXid", value = "用户名或用户ID", required = true) @PathVariable("usernameXid") String usernameXid
    ) {
        SysUser sysUser = null;
        if (usernameXid.matches("[0-9]{19,}")) {
            sysUser = iSysUserService.getById(usernameXid);
        } else {
            sysUser = iSysUserService.getUserByUsername(usernameXid);
        }
        if (ObjectUtils.isEmpty(sysUser)) {
            return ApiResult.failed("用户不存在");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("user", sysUser);
        return ApiResult.success(map);
    }

    /**
     * 获取当前用户信息
     * 含话题
     * 登录用户
     *
     * @param  principal
     * @param  pageNum
     * @param  pageSize
     * @return ApiResult
     */
    @ApiOperation(value = "获取当前用户信息 含话题")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ApiResult<Map<String, Object>> getUser(
            Principal principal,
            @ApiParam(name = "pageNum", value = "页码:默认1", required = true) @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(name = "pageSize", value = "每页数据量:默认10", required = true) @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize

    ) {
        SysUser sysUser = iSysUserService.getUserByUsername(principal.getName());
        if (ObjectUtils.isEmpty(sysUser)) {
            return ApiResult.failed("用户不存在");
        }
        Page<SysPost> page = iSysPostService.page(new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<SysPost>().eq(SysPost::getUserId, sysUser.getId()));
        Map<String, Object> map = new HashMap<>(16);
        map.put("user", sysUser);
        map.put("posts", page);
        return ApiResult.success(map);
    }

    /**
     * 获取指定用户信息
     * 含话题
     *
     * @param  usernameXid
     * @param  pageNum
     * @param  pageSize
     * @return ApiResult
     */
    @ApiOperation(value = "获取指定用户信息 含话题")
    @RequestMapping(value = "/user/{usernameXid}", method = RequestMethod.GET)
    public ApiResult<Map<String, Object>> getUserByUsernameOrId(
            @ApiParam(name = "usernameXid", value = "用户名或用户ID", required = true) @PathVariable("usernameXid") String usernameXid,
            @ApiParam(name = "pageNum", value = "页码:默认1", required = true) @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(name = "pageSize", value = "每页数据量:默认10", required = true) @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize

    ) {
        SysUser sysUser = null;
        if (usernameXid.matches("[0-9]{19,}")) {
            sysUser = iSysUserService.getById(usernameXid);
        } else {
            sysUser = iSysUserService.getUserByUsername(usernameXid);
        }
        if (ObjectUtils.isEmpty(sysUser)) {
            return ApiResult.failed("用户不存在");
        }
        Page<SysPost> page = iSysPostService.page(new Page<>(pageNum, pageSize),
                new LambdaQueryWrapper<SysPost>().eq(SysPost::getUserId, sysUser.getId()));
        Map<String, Object> map = new HashMap<>(16);
        map.put("user", sysUser);
        map.put("posts", page);
        return ApiResult.success(map);
    }

    /**
     * 获取所有用户信息
     * 超级管理员
     * 管理员
     *
     * @return ApiResult
     */
    @ApiOperation(value = "管理员获取所有用户信息")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/user", method = RequestMethod.GET)
    public ApiResult<Page<SysUser>> getUsersByAdmin(
            @ApiParam(name = "pageNum", value = "页码:默认1", required = true) @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(name = "pageSize", value = "每页数据量:默认10", required = true) @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        Page<SysUser> page = iSysUserService.getListByAdmin(new Page<>(pageNum, pageSize));
        return ApiResult.success(page);
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
     * @param  sysUser
     * @return ApiResult
     */
    @ApiOperation(value = "管理员修改用户信息")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/user", method = RequestMethod.PUT)
    public ApiResult<Object> updateUserByAdmin(@RequestBody SysUser sysUser) {
        try {
            iSysUserService.updateById(sysUser);
            return ApiResult.success(sysUser, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResult.failed("修改失败");
    }

    /**
     * 修改密码
     * 登录用户
     *
     * @param  oldPass
     * @param  newPass
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "修改密码")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    public ApiResult<String> updatePassword(
            @RequestParam(value = "old") String oldPass,
            @RequestParam(value = "new") String newPass,
            Principal principal
    ) {
        SysUser user = iSysUserService.getUserByUsername(principal.getName());
        if (!user.getActive()) {
            return ApiResult.failed("账号未激活");
        }
        if (iSysUserService.changePassword(user.getId(), oldPass, newPass)) {
            return ApiResult.success("操作成功");
        }
        return ApiResult.failed("操作失败");
    }

}
