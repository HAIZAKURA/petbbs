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
import run.nya.petbbs.model.dto.SectionDTO;
import run.nya.petbbs.model.entity.SysSection;
import run.nya.petbbs.model.entity.SysUser;
import run.nya.petbbs.service.ISysSectionService;
import run.nya.petbbs.service.ISysUserService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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

    @Resource
    private ISysUserService iSysUserService;

    /**
     * 获取所有有效专栏
     *
     * @param  pageNum
     * @param  pageSize
     * @return ApiResult
     */
    @ApiOperation(value = "获取所有有效专栏")
    @RequestMapping(value = "/section", method = RequestMethod.GET)
    public ApiResult<Page<SysSection>> getSections(
            @ApiParam(name = "pageNum", value = "页码:默认0", required = true) @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
            @ApiParam(name = "pageSize", value = "每页数据量:默认10", required = true) @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        Page<SysSection> page = iSysSectionService.page(new Page<>(pageNum, pageSize), new LambdaQueryWrapper<SysSection>().eq(SysSection::getState, true));
        return ApiResult.success(page);
    }

    /**
     * 获取所有专栏
     *
     * @param  pageNum
     * @param  pageSize
     * @return ApiResult
     */
    @ApiOperation(value = "获取所有有效专栏")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/section", method = RequestMethod.GET)
    public ApiResult<Page<SysSection>> getAllSections(
            @ApiParam(name = "pageNum", value = "页码:默认0", required = true) @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
            @ApiParam(name = "pageSize", value = "每页数据量:默认10", required = true) @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        Page<SysSection> page = iSysSectionService.page(new Page<>(pageNum, pageSize), null);
        return ApiResult.success(page);
    }

    /**
     * 获取当前用户专栏
     *
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "获取当前用户专栏")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/my/section", method = RequestMethod.GET)
    public ApiResult<List<SysSection>> getUserSections(Principal principal) {
        List<SysSection> sectionList = new ArrayList<>(16);
        SysUser sysUser = iSysUserService.getUserByUsername(principal.getName());
        sectionList = iSysSectionService.list(new LambdaQueryWrapper<SysSection>().eq(SysSection::getUserId, sysUser.getId()));
        return ApiResult.success(sectionList);
    }

    /**
     * 获取专栏信息
     *
     * @param  id
     * @return ApiResult
     */
    @ApiOperation(value = "获取专栏信息")
    @RequestMapping(value = "/section/{id}", method = RequestMethod.GET)
    public ApiResult<SysSection> getSection(
            @ApiParam(name = "id", value = "id", required = true) @PathVariable("id") String id
    ) {
        LambdaQueryWrapper<SysSection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysSection::getId, id);
        SysSection section = iSysSectionService.getOne(wrapper);
        if (ObjectUtils.isEmpty(section)) {
            return ApiResult.failed("专栏不存在");
        }
        return ApiResult.success(section);
    }

    /**
     * 申请专栏
     *
     * @param  dto
     * @return ApiResult
     */
    @ApiOperation(value = "申请专栏")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/section", method = RequestMethod.POST)
    public ApiResult<SysSection> addSection(@Valid @RequestBody SectionDTO dto, Principal principal) {
        SysUser sysUser = iSysUserService.getUserByUsername(principal.getName());
        SysSection sysSection = iSysSectionService.addSection(dto, sysUser.getId());
        if (ObjectUtils.isEmpty(sysSection)) {
            return ApiResult.failed("添失败");
        }
        return ApiResult.success(sysSection);
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
