package run.nya.petbbs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import run.nya.petbbs.common.api.ApiResult;
import run.nya.petbbs.model.dto.CreateVideoDTO;
import run.nya.petbbs.model.entity.SysUser;
import run.nya.petbbs.model.entity.SysVideoPost;
import run.nya.petbbs.model.vo.VideoPostVO;
import run.nya.petbbs.service.ISysUserService;
import run.nya.petbbs.service.ISysVideoPostService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 视频话题控制器
 *
 * 2021/03/07
 */
@RestController
@RequestMapping("/api")
@Api(tags = {"SysVideoPostController"})
public class SysVideoPostController extends BaseController {

    @Resource
    private ISysVideoPostService iSysVideoPostService;

    @Resource
    private ISysUserService iSysUserService;

    /**
     * 获取视频列表
     *
     * @param  tab
     * @param  tagId
     * @param  pageNum
     * @param  pageSize
     * @return ApiResult
     */
//    @ApiOperation(value = "获取视频列表")
//    @RequestMapping(value = "/video", method = RequestMethod.GET)
//    public ApiResult<Page<VideoPostVO>> getList(
//            @ApiParam(name = "tab", value = "类别:latest|最新;hot|热门;默认latest", required = true) @RequestParam(value = "tab", defaultValue = "latest") String tab,
//            @ApiParam(name = "tid", value = "标签ID", required = false) @RequestParam(value = "tid", required = false) String tagId,
//            @ApiParam(name = "pageNum", value = "页码:默认1", required = true) @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
//            @ApiParam(name = "pageSize", value = "每页数据量:默认10", required = true) @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
//
//    ) {
//        Page<VideoPostVO> page = iSysVideoPostService.getList(new Page<>(pageNum, pageSize), tab, tagId);
//        return ApiResult.success(page);
//    }

    /**
     * 获取指定视频
     *
     * @param  id
     * @return ApiResult
     */
//    @ApiOperation(value = "获取指定视频")
//    @RequestMapping(value = "/video/{id}", method = RequestMethod.GET)
//    public ApiResult<Map<String, Object>> getVideo(@PathVariable("id") String id) {
//        Map<String, Object> map = iSysVideoPostService.viewVideo(id);
//        return ApiResult.success(map);
//    }

    /**
     * 获取推荐视频列表
     *
     * @param  id
     * @return ApiRsult
     */
//    @ApiOperation(value = "获取推荐视频列表")
//    @RequestMapping(value = "/video/recommend", method = RequestMethod.GET)
//    public ApiResult<List<VideoPostVO>> getRecommend(
//            @ApiParam(name = "videoId", value = "视频ID", required = true) @RequestParam("videoId") String id
//    ) {
//        List<VideoPostVO> list = iSysVideoPostService.getRecommend(id);
//        return ApiResult.success(list);
//    }

    /**
     * 创建视频
     * 登录用户
     *
     * @param  dto
     * @param  principal
     * @return ApiResult
     */
//    @ApiOperation(value = "创建视频")
//    @PreAuthorize("isAuthenticated()")
//    @RequestMapping(value = "/video", method = RequestMethod.POST)
//    public ApiResult<SysVideoPost> create(@RequestBody CreateVideoDTO dto, Principal principal) {
//        SysUser user = iSysUserService.getUserByUsername(principal.getName());
//        if (!user.getActive()) {
//            return ApiResult.failed("账号未激活");
//        }
//        SysVideoPost videoPost = iSysVideoPostService.create(dto, user);
//        return ApiResult.success(videoPost);
//    }

    /**
     * 修改视频
     * 登录用户
     *
     * @param  videoPost
     * @param  principal
     * @return ApiResult
     */
//    @ApiOperation(value = "修改视频")
//    @PreAuthorize("isAuthenticated()")
//    @RequestMapping(value = "/video", method = RequestMethod.PUT)
//    public ApiResult<SysVideoPost> update(@Valid @RequestBody SysVideoPost videoPost, Principal principal) {
//        SysUser user = iSysUserService.getUserByUsername(principal.getName());
//        if (!user.getId().equals(videoPost.getUserId())) {
//            return ApiResult.failed("不能修改别人的视频");
//        }
//        videoPost.setModifyTime(new Date());
//        iSysVideoPostService.updateById(videoPost);
//        return ApiResult.success(videoPost);
//    }

    /**
     * 修改视频
     * 超级管理员
     * 管理员
     *
     * @param  videoPost
     * @return ApiResult
     */
//    @ApiOperation(value = "管理员修改视频")
//    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
//    @RequestMapping(value = "/admin/video", method = RequestMethod.PUT)
//    public ApiResult<SysVideoPost> updateByAdmin(@Valid @RequestBody SysVideoPost videoPost) {
//        videoPost.setModifyTime(new Date());
//        iSysVideoPostService.updateById(videoPost);
//        return ApiResult.success(videoPost);
//    }

    /**
     * 删除视频
     * 登录用户
     *
     * @param  id
     * @param  principal
     * @return ApiResult
     */
//    @ApiOperation(value = "删除视频")
//    @PreAuthorize("isAuthenticated()")
//    @RequestMapping(value = "/video/{id}", method = RequestMethod.DELETE)
//    public ApiResult<String> delete(@PathVariable("id") String id, Principal principal) {
//        SysUser user = iSysUserService.getUserByUsername(principal.getName());
//        SysVideoPost videoPost = iSysVideoPostService.getById(id);
//        if (ObjectUtils.isEmpty(videoPost)) {
//            return ApiResult.failed("视频不存在");
//        }
//        if (!user.getId().equals(videoPost.getUserId())) {
//            return ApiResult.failed("不能删除别人的视频");
//        }
//        iSysVideoPostService.removeById(id);
//        return ApiResult.success("删除成功");
//    }

    /**
     * 删除视频
     * 超级管理员
     * 管理员
     *
     * @param  id
     * @return ApiResult
     */
//    @ApiOperation(value = "管理员删除视频")
//    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
//    @RequestMapping(value = "/admin/video/{id}", method = RequestMethod.DELETE)
//    public ApiResult<String> deleteByAdmin(@PathVariable("id") String id) {
//        SysVideoPost videoPost = iSysVideoPostService.getById(id);
//        if (ObjectUtils.isEmpty(videoPost)) {
//            return ApiResult.failed("视频不存在");
//        }
//        iSysVideoPostService.removeById(id);
//        return ApiResult.success("删除成功");
//    }

}
