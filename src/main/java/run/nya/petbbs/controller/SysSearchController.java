package run.nya.petbbs.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import run.nya.petbbs.common.api.ApiResult;
import run.nya.petbbs.mapper.SysPostMapper;
import run.nya.petbbs.model.entity.SysPost;
import run.nya.petbbs.model.vo.PostVO;
import run.nya.petbbs.service.ISysPostService;

import javax.annotation.Resource;

/**
 * 搜索控制器
 *
 * 2021/03/02
 */
@RestController
@RequestMapping("/api")
@Api(tags = {"SysSearchController"})
public class SysSearchController extends BaseController {

    @Resource
    private ISysPostService iSysPostService;

    @Autowired
    private SysPostMapper sysPostMapper;

    /**
     * 通过关键词搜索话题
     *
     * @param  keyword
     * @param  pageNum
     * @param  pageSize
     * @return ApiResult
     */
    @ApiOperation(value = "通过关键词搜索话题")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ApiResult<Page<PostVO>> searchPosts(
            @ApiParam(name = "keyword", value = "关键词", required = true) @RequestParam(value = "keyword") String keyword,
            @ApiParam(name = "pageNum", value = "页码:默认1", required = true) @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @ApiParam(name = "pageSize", value = "每页数据量:默认10", required = true) @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize

    ) {
        Page<PostVO> res = iSysPostService.searchByKey(new Page<>(pageNum, pageSize), keyword);
        return ApiResult.success(res);
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    public ApiResult<SysPost> randomPost() {
        SysPost post = sysPostMapper.randomPost();
        return ApiResult.success(post);
    }

}
