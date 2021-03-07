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
     * @param  pageNum
     * @param  pageSize
     * @return ApiResult
     */
    @ApiOperation(value = "获取所有标签")
    @RequestMapping(value = "/tag", method = RequestMethod.GET)
    public ApiResult<Page<SysTag>> getTags(
            @ApiParam(name = "pageNum", value = "页码:默认0", required = true) @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
            @ApiParam(name = "pageSize", value = "每页数据量:默认10", required = true) @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        Page<SysTag> page = iSysTagService.page(new Page<>(pageNum, pageSize), null);
        return ApiResult.success(page);
    }

    /**
     * 添加标签
     * 超级管理员
     * 管理员
     *
     * @param  tags
     * @return ApiResult
     */
    @ApiOperation(value = "管理员添加标签")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/tag", method = RequestMethod.POST)
    public ApiResult<Map<String, Object>> addTags(@Valid @RequestBody List<String> tags) {
        List<SysTag> list = iSysTagService.addTags(tags);
        if (ObjectUtils.isEmpty(list)) {
            return ApiResult.failed("添加失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("tags", list);
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

    /**
     * 删除标签
     * 超级管理员
     * 管理员
     *
     * @param  id
     * @return ApiResult
     */
    @ApiOperation(value = "管理员删除标签")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/tag/{id}", method = RequestMethod.DELETE)
    public ApiResult<String> deleteTag(@PathVariable("id") String id) {
        SysTag sysTag = iSysTagService.getById(id);
        if (ObjectUtils.isEmpty(sysTag)) {
            return ApiResult.failed("标签不存在");
        }
        iSysTagService.removeById(id);
        return ApiResult.failed("删除成功");
    }

}
