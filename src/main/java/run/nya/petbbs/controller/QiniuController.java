package run.nya.petbbs.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import run.nya.petbbs.common.api.ApiResult;
import run.nya.petbbs.model.dto.QiniuDTO;
import run.nya.petbbs.model.entity.QiniuConfig;
import run.nya.petbbs.service.IQiniuConfigService;

import javax.annotation.Resource;

/**
 * 七牛控制器
 *
 * 2022/02/22
 */
@RestController
@RequestMapping("/api")
@Api(tags = {"QiniuController"})
public class QiniuController extends BaseController {

    @Resource
    private IQiniuConfigService iQiniuConfigService;

    /**
     * 获取七牛配置
     * 超级管理员
     *
     * @return ApiResult
     */
    @ApiOperation(value = "超级管理员获取七牛配置")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/admin/qiniu", method = RequestMethod.GET)
    public ApiResult<QiniuConfig> getQiniuConfig() {
        QiniuConfig qiniuConfig = iQiniuConfigService.getQiniuConfig();
        return ApiResult.success(qiniuConfig);
    }

    /**
     * 修改七牛配置
     * 超级管理员
     *
     * @param  dto
     * @return ApiResult
     */
    @ApiOperation(value = "超级管理员修改七牛配置")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/admin/qiniu", method = RequestMethod.PUT)
    public ApiResult<String> updateQiniuConfig(@RequestBody QiniuDTO dto) {
        if (iQiniuConfigService.updateQiniuConfig(dto) > 0) {
            return ApiResult.success("修改成功");
        }
        return ApiResult.failed("修改失败");
    }

}
