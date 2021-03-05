package run.nya.petbbs.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.nya.petbbs.service.ISysCommentService;

import javax.annotation.Resource;

/**
 * 评论控制器
 *
 * 2021/03/05
 */
@RestController
@RequestMapping("/api")
@Api(tags = {"SysCommentController"})
public class SysCommentController extends BaseController {

    @Resource
    private ISysCommentService iSysCommentService;

}
