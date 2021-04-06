package run.nya.petbbs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import run.nya.petbbs.common.api.ApiResult;
import run.nya.petbbs.model.entity.SysSensitiveWord;
import run.nya.petbbs.service.ISysSensitiveWordService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 敏感词控制器
 *
 * 2021/03/07
 */
@RestController
@RequestMapping("/api")
@Api(tags = {"SysSensitiveWordController"})
public class SysSensitiveWordController extends BaseController {

    @Resource
    private ISysSensitiveWordService iSysSensitiveWordService;

    /**
     * 获取敏感词列表
     * 超级管理员
     * 管理员
     *
     * @return ApiResult
     */
    @ApiOperation(value = "管理员获取敏感词列表")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/word", method = RequestMethod.GET)
    public ApiResult<List<SysSensitiveWord>> getList() {
        List<SysSensitiveWord> list = iSysSensitiveWordService.list();
        return ApiResult.success(list);
    }

    /**
     * 添加敏感词
     * 超级管理员
     * 管理员
     *
     * @param  words
     * @return ApiResult
     */
    @ApiOperation(value = "管理员添加敏感词")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/word", method = RequestMethod.POST)
    public ApiResult<Map<String, Object>> add(@Valid @RequestBody List<String> words) {
        Map<String, Object> map = new HashMap<>(16);
        List<SysSensitiveWord> list = iSysSensitiveWordService.add(words);
        if (ObjectUtils.isEmpty(list)) {
            return ApiResult.failed("添加失败");
        }
        map.put("words", list);
        return ApiResult.success(map);
    }

    /**
     * 删除敏感词
     * 超级管理员
     * 管理员
     *
     * @param  id
     * @return ApiResult
     */
    @ApiOperation(value = "管理员删除敏感词")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/admin/word", method = RequestMethod.DELETE)
    public ApiResult<String> delete(@RequestParam(value = "id") Integer id) {
        LambdaQueryWrapper<SysSensitiveWord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysSensitiveWord::getId, id);
        SysSensitiveWord sysSensitiveWord = iSysSensitiveWordService.getOne(wrapper);
        if (ObjectUtils.isEmpty(sysSensitiveWord)) {
            return ApiResult.failed("敏感词不存在");
        }
        iSysSensitiveWordService.remove(wrapper);
        return ApiResult.success("删除成功");
    }

//    @RequestMapping(value = "/test", method = RequestMethod.POST)
//    public ApiResult<String> test(@RequestBody String text) {
//        List<String> list = iSysSensitiveWordService.getWords();
//        for (String word : list) {
//            text = text.replace(word, "***");
//        }
//        return ApiResult.success(text);
//    }

}
