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
import run.nya.petbbs.model.dto.BillboardDTO;
import run.nya.petbbs.model.entity.SysBillboard;
import run.nya.petbbs.service.ISysBillboardService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公告控制器
 *
 * 2021/02/26
 */
@RestController
@RequestMapping("/api")
@Api(tags = {"SysBillboardController"})
public class SysBillboardController extends BaseController {

    @Resource
    private ISysBillboardService iSysBillboardService;

    /**
     * 获取有效公告
     *
     * @return ApiResult
     */
    @ApiOperation(value = "获取有效公告")
    @RequestMapping(value = "/billboard", method = RequestMethod.GET)
    public ApiResult<List<SysBillboard>> getBillboard() {
        List<SysBillboard> list = iSysBillboardService.list(new LambdaQueryWrapper<SysBillboard>().eq(SysBillboard::getState, true));
        return ApiResult.success(list);
    }

    /**
     * 获取所有公告
     * 超级管理员
     *
     * @return ApiResult
     */
    @ApiOperation(value = "获取所有公告")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/admin/billboard", method = RequestMethod.GET)
    public ApiResult<List<SysBillboard>> getAllBillboard() {
        List<SysBillboard> list = iSysBillboardService.list();
        return ApiResult.success(list);
    }

    /**
     * 添加公告
     * 超级管理员
     *
     * @param  dto
     * @return ApiResult
     */
    @ApiOperation(value = "添加公告")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/admin/billboard", method = RequestMethod.POST)
    public ApiResult<Map<String, Object>> addBillboard(@Valid @RequestBody BillboardDTO dto) {
        SysBillboard sysBillboard = iSysBillboardService.addBillboard(dto);
        if (ObjectUtils.isEmpty(sysBillboard)) {
            return ApiResult.failed("添加失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("billboard", sysBillboard);
        return ApiResult.success(map);
    }

    /**
     * 修改公告
     * 超级管理员
     *
     * @param  sysBillboard
     * @return ApiResult
     */
    @ApiOperation(value = "修改公告")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/admin/billboard", method = RequestMethod.PUT)
    public ApiResult<String> updateBillboard(@RequestBody SysBillboard sysBillboard) {
        iSysBillboardService.updateById(sysBillboard);
        return ApiResult.success("修改成功");
    }

}
