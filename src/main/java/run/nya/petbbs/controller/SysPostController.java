package run.nya.petbbs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import run.nya.petbbs.common.api.ApiResult;
import run.nya.petbbs.model.dto.CreatePostDTO;
import run.nya.petbbs.model.entity.SysPost;
import run.nya.petbbs.model.entity.SysUser;
import run.nya.petbbs.model.vo.PostVO;
import run.nya.petbbs.service.ISysPostService;
import run.nya.petbbs.service.ISysUserService;

import javax.annotation.Resource;
import java.security.Principal;

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

    /**
     * 获取话题列表
     *
     * @param  tab
     * @param  pageNo
     * @param  pageSize
     * @return ApiResult
     */
    @ApiOperation(value = "获取话题列表")
    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public ApiResult<Page<PostVO>> postList(
            @ApiParam("类别:latest|最新;hot|热门;essence|加精;top|置顶;默认latest") @RequestParam(value = "tab", defaultValue = "latest") String tab,
            @ApiParam("页码:默认1") @RequestParam(value = "pageNo", defaultValue = "1") Integer pageNo,
            @ApiParam("每页数据量:默认10") @RequestParam(value = "size", defaultValue = "10") Integer pageSize
    ) {
        Page<PostVO> page = iSysPostService.getList(new Page<>(pageNo, pageSize), tab);
        return ApiResult.success(page);
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
    public ApiResult<SysPost> createPost(@RequestBody CreatePostDTO dto, Principal principal) {
        SysUser sysUser = iSysUserService.getUserByUsername(principal.getName());
        Assert.isTrue(sysUser.getActive(), "账号未激活");
        SysPost sysPost = iSysPostService.create(dto, sysUser);
        return ApiResult.success(sysPost);
    }

}
