package run.nya.petbbs.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import run.nya.petbbs.common.api.ApiResult;
import run.nya.petbbs.model.dto.TagDTO;
import run.nya.petbbs.model.entity.SysTag;
import run.nya.petbbs.service.ISysTagService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标签控制器
 *
 * 2021/02/27
 */
@RestController
@RequestMapping("/api")
@Api(tags = {"SysTagController"})
public class SysTagController extends BaseController {

    @Resource
    private ISysTagService iSysTagService;

    /**
     * 获取所有标签
     *
     * @return ApiResult
     */
    @ApiOperation(value = "获取所有标签")
    @RequestMapping(value = "/tag", method = RequestMethod.GET)
    public ApiResult<List<SysTag>> getTags() {
        List<SysTag> list = iSysTagService.list();
        return ApiResult.success(list);
    }

    /**
     * 添加标签
     * 超级管理员
     * 管理员
     *
     * @param  dto
     * @return ApiResult
     */
    @ApiOperation(value = "管理员添加标签")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/tag", method = RequestMethod.POST)
    public ApiResult<Map<String, Object>> addTag(@Valid @RequestBody TagDTO dto) {
        SysTag sysTag = iSysTagService.addTag(dto);
        if (ObjectUtils.isEmpty(sysTag)) {
            return ApiResult.failed("添加失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("tag", sysTag);
        return ApiResult.success(map);
    }

    /**
     * 修改标签
     * 超级管理员
     * 管理员
     *
     * @param  sysTag
     * @return ApiResult
     */
    @ApiOperation(value = "管理员修改标签")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/tag", method = RequestMethod.PUT)
    public ApiResult<String> updateTag(@RequestBody SysTag sysTag) {
        try {
            iSysTagService.updateById(sysTag);
            return ApiResult.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResult.failed("修改失败");
    }

}
