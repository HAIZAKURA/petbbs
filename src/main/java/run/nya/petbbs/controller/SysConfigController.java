package run.nya.petbbs.controller;

import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import run.nya.petbbs.common.api.ApiResult;
import run.nya.petbbs.common.exception.ApiAsserts;
import run.nya.petbbs.model.entity.SysComment;
import run.nya.petbbs.model.entity.SysConfig;
import run.nya.petbbs.model.entity.SysPost;
import run.nya.petbbs.service.*;
import run.nya.petbbs.util.SysMailUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设置控制器
 *
 * 2021/03/06
 */
@RestController
@RequestMapping("/api")
@Api(tags = {"SysConfigController"})
public class SysConfigController extends BaseController {

    @Resource
    private ISysConfigService iSysConfigService;

    @Resource
    private ISysPostService iSysPostService;

    @Resource
    private ISysUserService iSysUserService;

    @Resource
    private ISysPhotoService iSysPhotoService;

    @Resource
    private ISysCommentService iSysCommentService;

    /**
     * 获取网站信息
     *
     * @return ApiResult
     */
    @ApiOperation(value = "获取网站信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ApiResult<Map<String, String>> getSiteInfo() {
        Map<String, String> map = new HashMap<>(16);
        List<SysConfig> configs = iSysConfigService.list(new LambdaQueryWrapper<SysConfig>().likeRight(SysConfig::getItem, "site_"));
        if (ObjectUtils.isEmpty(configs)) {
            return ApiResult.failed("获取失败");
        }
        configs.forEach(config -> {
            map.put(config.getItem(), config.getValue());
        });
        return ApiResult.success(map);
    }

    /**
     * 获取设置
     * 超级管理员
     *
     * @return ApiResult
     */
    @ApiOperation(value = "获取设置")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public ApiResult<Map<String, String>> getConfigs() {
        Map<String, String> map = new HashMap<>(16);
        List<SysConfig> configs = iSysConfigService.list();
        if (ObjectUtils.isEmpty(configs)) {
            return ApiResult.failed("获取失败");
        }
        configs.forEach(config -> {
            map.put(config.getItem(), config.getValue());
        });
        return ApiResult.success(map);
    }

    /**
     * 修改设置
     * 超级管理员
     *
     * @param  configs
     * @return ApiResult
     */
    @ApiOperation(value = "修改设置")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/config", method = RequestMethod.PUT)
    public ApiResult<String> update(@RequestBody List<SysConfig> configs) {
        configs.forEach(config -> {
            iSysConfigService.update(config, new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getItem, config.getItem()));
        });
        return ApiResult.success("修改成功");
    }

    /**
     * email测试
     * 超级管理员
     *
     * @param  mailto
     * @return ApiResult
     */
    @ApiOperation(value = "email测试")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/mail_test", method = RequestMethod.GET)
    public ApiResult<String> mailTest(@RequestParam("mailto") String mailto) {
        ThreadUtil.execAsync(() -> {
            try {
                SysMailUtil sysMailUtil = new SysMailUtil();
                sysMailUtil.sendMail(mailto, "系统测试", "这是一封测试邮件。", false);
            } catch (Exception e) {
                ApiAsserts.fail("发送失败！");
            }
        });
        return ApiResult.success("发送成功");
    }

    /**
     * 获取总览数据
     *
     * @return ApiResult
     */
    @ApiOperation(value = "获取总览数据")
    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    public ApiResult<Map<String, Object>> getSummary() {
        Map<String, Object> map = new HashMap<>(16);
        map.put("userCount", iSysUserService.count());
        map.put("postCount", iSysPostService.count());
        map.put("photoCount", iSysPhotoService.count());
        map.put("commentCount", iSysCommentService.count());
        map.put("postRecent", iSysPostService.list(new QueryWrapper<SysPost>().orderByDesc("create_time").last("limit 5")));
        map.put("commentRecent", iSysCommentService.list(new QueryWrapper<SysComment>().orderByDesc("create_time").last("limit 5")));
        return ApiResult.success(map);
    }

}
