package run.nya.petbbs.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import run.nya.petbbs.common.api.ApiResult;
import run.nya.petbbs.model.dto.QiniuDTO;
import run.nya.petbbs.model.entity.QiniuConfig;
import run.nya.petbbs.service.IQiniuConfigService;
import run.nya.petbbs.util.QiniuUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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
    @RequestMapping(value = "/qiniu", method = RequestMethod.GET)
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
    @RequestMapping(value = "/qiniu", method = RequestMethod.PUT)
    public ApiResult<String> updateQiniuConfig(@RequestBody QiniuDTO dto) {
        if (iQiniuConfigService.updateQiniuConfig(dto) > 0) {
            return ApiResult.success("修改成功");
        }
        return ApiResult.failed("修改失败");
    }

    /**
     * 上传图片
     * 登录用户
     *
     * @param  file
     * @param  name
     * @return ApiResult
     */
    @ApiOperation(value = "上传图片")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/qiniu", method = RequestMethod.POST)
    public ApiResult<Map<String, String>> uploadImg(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) {
        Map<String, String> map = new HashMap<>(128);
        try {
            QiniuUtil qiniuUtil = new QiniuUtil();
            QiniuConfig qiniuConfig = iQiniuConfigService.getQiniuConfig();
            byte[] byteFile = file.getBytes();
            map = qiniuUtil.uploadImg(qiniuConfig, byteFile, name);
        } catch (Exception e) {
            return ApiResult.failed("上传失败");
        }
        return ApiResult.success(map, "上传成功");
    }

}
