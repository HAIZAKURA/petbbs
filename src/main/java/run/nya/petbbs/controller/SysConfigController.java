package run.nya.petbbs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import run.nya.petbbs.common.api.ApiResult;
import run.nya.petbbs.model.entity.SysConfig;
import run.nya.petbbs.service.ISysConfigService;

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

}
