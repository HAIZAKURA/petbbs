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
import run.nya.petbbs.model.dto.FriendLinkDTO;
import run.nya.petbbs.model.entity.SysFriendLink;
import run.nya.petbbs.service.ISysFriendLinkService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 友链控制器
 *
 * 2021/02/26
 */
@RestController
@RequestMapping("/api")
@Api(tags = {"SysFriendLinkController"})
public class SysFriendLinkController extends BaseController {

    @Resource
    private ISysFriendLinkService iSysFriendLinkService;

    /**
     * 获取有效友链
     *
     * @return ApiResult
     */
    @ApiOperation(value = "获取有效友链")
    @RequestMapping(value = "/friends", method = RequestMethod.GET)
    public ApiResult<List<SysFriendLink>> getLinks() {
        LambdaQueryWrapper<SysFriendLink> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysFriendLink::getState, true);
        List<SysFriendLink> list = iSysFriendLinkService.list(wrapper);
        return ApiResult.success(list);
    }

    /**
     * 获取所有友链
     * 超级管理员
     *
     * @return ApiResult
     */
    @ApiOperation(value = "获取所有友链")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/admin/friends", method = RequestMethod.GET)
    public ApiResult<List<SysFriendLink>> getLinksByAdmin() {
        List<SysFriendLink> list = iSysFriendLinkService.list();
        return ApiResult.success(list);
    }

    /**
     * 添加友链
     * 超级管理员
     *
     * @param  dto
     * @return ApiResult
     */
    @ApiOperation(value = "添加友链")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/admin/friends", method = RequestMethod.POST)
    public ApiResult<Map<String, Object>> addLink(@Valid @RequestBody FriendLinkDTO dto) {
        SysFriendLink sysFriendLink = iSysFriendLinkService.addLink(dto);
        if (ObjectUtils.isEmpty(sysFriendLink)) {
            return ApiResult.failed("添加失败");
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("friends", sysFriendLink);
        return ApiResult.success(map);
    }

    /**
     * 修改友链
     * 超级管理员
     *
     * @param  sysFriendLink
     * @return ApiResult
     */
    @ApiOperation(value = "修改友链")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    @RequestMapping(value = "/admin/friends", method = RequestMethod.PUT)
    public ApiResult<String> updateLink(@RequestBody SysFriendLink sysFriendLink) {
        try {
            iSysFriendLinkService.updateById(sysFriendLink);
            return ApiResult.success("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResult.failed("修改失败");
    }

}
