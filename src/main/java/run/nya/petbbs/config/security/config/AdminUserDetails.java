package run.nya.petbbs.config.security.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import run.nya.petbbs.model.entity.SysUser;

import java.util.Collection;

/**
 * 用户登录数据
 *
 * 2021/02/18
 */
public class AdminUserDetails implements UserDetails {

    private final SysUser sysUser;

    public AdminUserDetails(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
        if (sysUser.getRoleId() != null) {
            if (sysUser.getRoleId() == 1) {
                return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_SUPERADMIN");
            } else if (sysUser.getRoleId() == 2) {
                return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN");
            } else if (sysUser.getRoleId() == 10000) {
                return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return sysUser.getStatus();
    }

}
