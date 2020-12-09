package run.nya.petbbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import run.nya.petbbs.service.UserService;

@RestController
public class UserController {

    @Autowired(required = false)
    private UserService userService;

    @GetMapping("/get_all_user")
    public Object getAllUser() {
        return userService.getAllUser();
    }
}
