package run.nya.petbbs.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import run.nya.petbbs.bean.SysUser;
import run.nya.petbbs.service.UserService;

import java.util.List;

@RestController
@Api(tags = {"Users"})
public class UserController {

    @Autowired(required = false)
    private UserService userService;

    @RequestMapping(value = "/api/user", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ApiOperation(value = "Get All Users", httpMethod = "GET", notes = "Admin")
    public Object getAllUser() {
        JSONObject res = new JSONObject();
        List<SysUser> userList = userService.getAllUser();
        res.put("data", userList);
        return res.toJSONString();
    }
}
