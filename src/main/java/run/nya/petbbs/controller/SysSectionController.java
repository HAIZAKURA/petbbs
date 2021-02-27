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
import run.nya.petbbs.model.dto.SectionDTO;
import run.nya.petbbs.model.entity.SysSection;
import run.nya.petbbs.service.ISysSectionService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 话题专栏控制器
 *
 * 2021/02/27
 */
@RestController
@RequestMapping("/api")
@Api(tags = {"SysSectionController"})
public class SysSectionController extends BaseController {

    @Resource
    private ISysSectionService iSysSectionService;

    /**
     * 获取话题专栏
     *
     * @return ApiResult
     */
    @ApiOperation(value = "获取话题专栏")
    @RequestMapping(value = "/section", method = RequestMethod.GET)
    public ApiResult<List<SysSection>> getSection() {
        List<SysSection> list = iSysSectionService.list();
        return ApiResult.success(list);
    }

    /**
     * 添加专栏
     * 超级管理员
     * 管理员
     *
     * @param  dto
     * @return ApiResult
     */
    @ApiOperation(value = "管理员添加专栏")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/section", method = RequestMethod.POST)
    public ApiResult<Map<String, Object>> addSection(@Valid @RequestBody SectionDTO dto) {
        SysSection sysSection = iSysSectionService.addSection(dto);
        if (ObjectUtils.isEmpty(sysSection)) {
            return ApiResult.failed("添加失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("section", sysSection);
        return ApiResult.success(map);
    }

    /**
     * 修改专栏
     * 超级管理员
     * 管理员
     *
     * @param  sysSection
     * @return ApiResult
     */
    @ApiOperation(value = "管理员修改专栏")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/section", method = RequestMethod.PUT)
    public ApiResult<String> updateSection(@RequestBody SysSection sysSection) {
        try {
            iSysSectionService.updateById(sysSection);
            return ApiResult.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResult.failed("修改失败");
    }

}
