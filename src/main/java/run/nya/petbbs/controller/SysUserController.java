package run.nya.petbbs.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.nya.petbbs.config.redis.RedisService;
import run.nya.petbbs.service.ISysUserService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sys/user")
@Api(tags = "SysUserController")
public class SysUserController extends BaseController {

    @Resource
    private ISysUserService iSysUserService;

    @Resource
    private RedisService redisService;


}
