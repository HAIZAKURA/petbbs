package run.nya.petbbs.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import run.nya.petbbs.common.api.ApiResult;
import run.nya.petbbs.model.entity.QiniuConfig;
import run.nya.petbbs.service.IQiniuConfigService;
import run.nya.petbbs.util.QiniuUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 上传控制器
 *
 * 2021/03/04
 */
@RestController
@RequestMapping("/api")
@Api(tags = {"SysUploadController"})
public class SysUploadController extends BaseController {

    @Resource
    private QiniuUtil qiniuUtil;

    @Resource
    private IQiniuConfigService iQiniuConfigService;

    /**
     * 上传文件
     * 登录用户
     *
     * @param  file
     * @param  name
     * @param  type
     * @return ApiResult
     */
    @ApiOperation(value = "上传文件")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ApiResult<Map<String, String>> upload(
            @ApiParam(name = "file", value = "文件", required = true) @RequestParam("file") MultipartFile file,
            @ApiParam(name = "name", value = "名称", required = true) @RequestParam("name") String name,
            @ApiParam(name = "type", value = "类型", required = true) @RequestParam("type") String type
    ) {
        Map<String, String> map = new HashMap<>(16);
        QiniuConfig config = iQiniuConfigService.getQiniuConfig();
        byte[] bytes = null;
        try {
            bytes = file.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResult.failed("文件上传失败");
        }
        if (Objects.equals(type, "image")) {
            map = qiniuUtil.uploadImg(config, bytes, name);
        } else if (Objects.equals(type, "video")) {
            map = qiniuUtil.uploadVideo(config, bytes, name);
        } else {
            return ApiResult.failed("文件类型错误");
        }
        return ApiResult.success(map);
    }

}
