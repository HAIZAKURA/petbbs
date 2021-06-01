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
import run.nya.petbbs.model.dto.CreatePhotoDTO;
import run.nya.petbbs.model.dto.NotifyDTO;
import run.nya.petbbs.model.entity.SysPhoto;
import run.nya.petbbs.model.entity.SysPostTag;
import run.nya.petbbs.model.entity.SysUser;
import run.nya.petbbs.model.vo.PhotoVO;
import run.nya.petbbs.service.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * 照片控制器
 *
 * 2021/03/14
 */
@RestController
@RequestMapping("/api")
@Api(tags = {"SysPhotoController"})
public class SysPhotoController extends BaseController {

    @Resource
    private ISysPhotoService iSysPhotoService;

    @Resource
    private ISysUserService iSysUserService;

    @Resource
    private ISysSensitiveWordService iSysSensitiveWordService;

    @Resource
    private ISysPostTagService iSysPostTagService;

    @Resource
    private ISysNotifyService iSysNotifyService;

    /**
     * 获取照片列表
     *
     * @param  pageNum
     * @param  pageSize
     * @return ApiResult
     */
    @ApiOperation(value = "获取照片列表")
    @RequestMapping(value = "/photo", method = RequestMethod.GET)
    public ApiResult<Page<PhotoVO>> getList(
            @ApiParam(name = "pageNum", value = "页码:默认1", required = true) @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(name = "pageSize", value = "每页数据量:默认10", required = true) @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        Page<PhotoVO> page = iSysPhotoService.getList(new Page<>(pageNum, pageSize));
        return ApiResult.success(page);
    }

    /**
     * 获取用户照片列表
     *
     * @param  userId
     * @param  pageNum
     * @param  pageSize
     * @return ApiResult
     */
    @ApiOperation(value = "获取用户照片列表")
    @RequestMapping(value = "/user/photo", method = RequestMethod.GET)
    public ApiResult<Page<SysPhoto>> getListByUser(
            @RequestParam(value = "userId") String userId,
            @ApiParam(name = "pageNum", value = "页码:默认1", required = true) @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(name = "pageSize", value = "每页数据量:默认10", required = true) @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize

    ) {
        Page<SysPhoto> page = iSysPhotoService.page(new Page<>(pageNum, pageSize), new LambdaQueryWrapper<SysPhoto>().eq(SysPhoto::getUserId, userId));
        return ApiResult.success(page);
    }

    /**
     * 获取照片
     *
     * @param  id
     * @return ApiResult
     */
    @ApiOperation(value = "获取照片")
    @RequestMapping(value = "/photo/{id}", method = RequestMethod.GET)
    public ApiResult<Map<String, Object>> getPhoto(@PathVariable("id") String id) {
        Map<String, Object> map = iSysPhotoService.viewPhoto(id);
        return ApiResult.success(map);
    }

    /**
     * 创建照片
     * 登录用户
     *
     * @param  dto
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "创建照片")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/photo", method = RequestMethod.POST)
    public ApiResult<SysPhoto> create(@Valid @RequestBody CreatePhotoDTO dto, Principal principal) {
        SysUser user = iSysUserService.getUserByUsername(principal.getName());
        if (!user.getActive()) {
            return ApiResult.failed("账号未激活");
        }
        String content = dto.getContent();
        List<String> words = iSysSensitiveWordService.getWords();
        for (String word : words) {
            content = content.replace(word, "***");
        }
        dto.setContent(content);
        SysPhoto photo = iSysPhotoService.create(dto, user);
        return ApiResult.success(photo);
    }

    /**
     * 删除照片
     * 登录用户
     *
     * @param  id
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "删除照片")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/photo/{id}", method = RequestMethod.DELETE)
    public ApiResult<String> delete(@PathVariable("id") String id, Principal principal) {
        SysUser user = iSysUserService.getUserByUsername(principal.getName());
        SysPhoto photo = iSysPhotoService.getById(id);
        if (ObjectUtils.isEmpty(photo)) {
            return ApiResult.failed("照片不存在");
        }
        if (!user.getId().equals(photo.getUserId())) {
            return ApiResult.failed("不能删除别人的照片哦");
        }
        iSysPhotoService.removeById(id);
        iSysPostTagService.removeById(new LambdaQueryWrapper<SysPostTag>().eq(SysPostTag::getPostId, id));
        return ApiResult.success("操作成功");
    }

    /**
     * 删除照片
     * 超级管理员
     * 管理员
     *
     * @param  id
     * @return ApiResult
     */
    @ApiOperation(value = "管理员删除照片")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/photo/{id}", method = RequestMethod.DELETE)
    public ApiResult<String> deleteByAdmin(@PathVariable("id") String id) {
        SysPhoto photo = iSysPhotoService.getById(id);
        if (ObjectUtils.isEmpty(photo)) {
            return ApiResult.failed("照片不存在");
        }
        iSysPhotoService.removeById(id);
        iSysPostTagService.removeById(new LambdaQueryWrapper<SysPostTag>().eq(SysPostTag::getPostId, id));
        return ApiResult.success("操作成功");
    }

    /**
     * 点赞照片
     *
     * @param  id
     * @return ApiResult
     */
    @ApiOperation(value = "点赞照片")
    @RequestMapping(value = "/photo/{id}", method = RequestMethod.PUT)
    public ApiResult<String> good(@PathVariable("id") String id) {
        SysPhoto photo = iSysPhotoService.getById(id);
        if (ObjectUtils.isEmpty(photo)) {
            return ApiResult.failed("照片不存在");
        }
        photo.setGood(photo.getGood() + 1);
        iSysPhotoService.updateById(photo);
        NotifyDTO dto = new NotifyDTO();
        dto.setUserId(photo.getUserId());
        dto.setContent("有人点赞了你的照片哦！");
        dto.setRemark("photos/" + id);
        iSysNotifyService.addNotify(dto);
        return ApiResult.success("操作成功");
    }

}
