package run.nya.petbbs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vdurmont.emoji.EmojiParser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import run.nya.petbbs.common.api.ApiResult;
import run.nya.petbbs.model.dto.CreatePostDTO;
import run.nya.petbbs.model.entity.SysPost;
import run.nya.petbbs.model.entity.SysUser;
import run.nya.petbbs.model.vo.PostVO;
import run.nya.petbbs.service.ISysPostService;
import run.nya.petbbs.service.ISysSensitiveWordService;
import run.nya.petbbs.service.ISysUserService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 话题控制器
 *
 * 2021/03/01
 */
@RestController
@RequestMapping("/api")
@Api(tags = {"SysPostController"})
public class SysPostController extends BaseController {

    @Resource
    private ISysPostService iSysPostService;

    @Resource
    private ISysUserService iSysUserService;

    @Resource
    private ISysSensitiveWordService iSysSensitiveWordService;

    /**
     * 获取话题列表
     *
     * @param  tab
     * @param  sectionId
     * @param  tagId
     * @param  pageNum
     * @param  pageSize
     * @return ApiResult
     */
    @ApiOperation(value = "获取话题列表")
    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public ApiResult<Page<PostVO>> getList(
            @ApiParam(name = "tab", value = "类别:latest|最新;hot|热门;essence|加精;top|置顶;默认latest", required = true) @RequestParam(value = "tab", defaultValue = "latest") String tab,
            @ApiParam(name = "sid", value = "专栏ID", required = false) @RequestParam(value = "sid", required = false) Integer sectionId,
            @ApiParam(name = "tid", value = "标签ID", required = false) @RequestParam(value = "tid", required = false) String tagId,
            @ApiParam(name = "pageNum", value = "页码:默认1", required = true) @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(name = "pageSize", value = "每页数据量:默认10", required = true) @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        Page<PostVO> page = iSysPostService.getList(new Page<>(pageNum, pageSize), tab, sectionId, tagId);
        return ApiResult.success(page);
    }

    /**
     * 获取指定话题
     *
     * @param  id
     * @return ApiResult
     */
    @ApiOperation(value = "获取指定话题")
    @RequestMapping(value = "/post/{id}", method = RequestMethod.GET)
    public ApiResult<Map<String, Object>> getPost(@PathVariable("id") String id) {
        Map<String, Object> map = iSysPostService.viewPost(id);
        return ApiResult.success(map);
    }

    /**
     * 创建话题
     * 登录用户
     *
     * @param  dto
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "创建话题")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public ApiResult<SysPost> create(@RequestBody CreatePostDTO dto, Principal principal) {
        SysUser sysUser = iSysUserService.getUserByUsername(principal.getName());
        if (!sysUser.getActive()) {
            return ApiResult.failed("账号未激活");
        }
        String content = dto.getContent();
        List<String> words = iSysSensitiveWordService.getWords();
        for (String word : words) {
            content = content.replace(word, "***");
        }
        dto.setContent(content);
        SysPost sysPost = iSysPostService.create(dto, sysUser);
        return ApiResult.success(sysPost);
    }

    /**
     * 修改话题
     * 登录用户
     *
     * @param  sysPost
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "修改话题")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/post", method = RequestMethod.PUT)
    public ApiResult<SysPost> update(@Valid @RequestBody SysPost sysPost, Principal principal) {
        SysUser sysUser = iSysUserService.getUserByUsername(principal.getName());
        if (!sysUser.getId().equals(sysPost.getUserId())) {
            return ApiResult.failed("不能修改别人的话题");
        }
        sysPost.setModifyTime(new Date());
        sysPost.setContent(EmojiParser.parseToAliases(sysPost.getContent()));
        iSysPostService.updateById(sysPost);
        return ApiResult.success(sysPost);
    }

    /**
     * 修改话题
     * 超级管理员
     * 管理员
     *
     * @param  sysPost
     * @return ApiResult
     */
    @ApiOperation(value = "管理员修改话题")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/post", method = RequestMethod.PUT)
    public ApiResult<SysPost> updateByAdmin(@Valid @RequestBody SysPost sysPost) {
        sysPost.setModifyTime(new Date());
        sysPost.setContent(EmojiParser.parseToAliases(sysPost.getContent()));
        iSysPostService.updateById(sysPost);
        return ApiResult.success(sysPost);
    }

    /**
     * 删除话题
     *
     * @param  id
     * @param  principal
     * @return ApiResult
     */
    @ApiOperation(value = "删除话题")
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "/post/{id}", method = RequestMethod.DELETE)
    public ApiResult<String> delete(
            @ApiParam(name = "id", value = "话题ID", required = true) @PathVariable("id") String id,
            Principal principal
    ) {
        SysUser sysUser = iSysUserService.getUserByUsername(principal.getName());
        SysPost sysPost = iSysPostService.getById(id);
        if (ObjectUtils.isEmpty(sysPost)) {
            return ApiResult.failed("话题不存在");
        }
        if (!sysPost.getUserId().equals(sysUser.getId())) {
            return ApiResult.failed("不能删除别人的话题");
        }
        iSysPostService.removeById(id);
        return ApiResult.success("删除成功");
    }

    /**
     * 删除话题
     * 超级管理员
     * 管理员
     *
     * @param  id
     * @return ApiResult
     */
    @ApiOperation(value = "管理员删除话题")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/post/{id}", method = RequestMethod.DELETE)
    public ApiResult<String> deleteByAdmin(
            @ApiParam(name = "id", value = "话题ID", required = true) @PathVariable("id") String id
    ) {
        SysPost sysPost = iSysPostService.getById(id);
        if (ObjectUtils.isEmpty(sysPost)) {
            return ApiResult.failed("话题不存在");
        }
        iSysPostService.removeById(id);
        return ApiResult.success("删除成功");
    }

    /**
     * 获取推荐话题列表
     *
     * @param  id
     * @return ApiResult
     */
    @ApiOperation(value = "获取推荐话题列表")
    @RequestMapping(value = "/post/recommend", method = RequestMethod.GET)
    public ApiResult<List<SysPost>> recommend(
            @ApiParam(name = "postId", value = "话题ID", required = true) @RequestParam("postId") String id
    ) {
        List<SysPost> posts = iSysPostService.getRecommend(id);
        return ApiResult.success(posts);
    }

}
