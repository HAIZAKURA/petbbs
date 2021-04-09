package run.nya.petbbs.config.security.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import run.nya.petbbs.model.entity.SysUser;
import run.nya.petbbs.service.ISysUserService;

import javax.annotation.Resource;

/**
 * 2021/02/18
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private ISysUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
        if (sysUser != null) {
            return new AdminUserDetails(sysUser);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }

}
