package run.nya.petbbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import run.nya.petbbs.model.dto.LoginDTO;
import run.nya.petbbs.model.dto.RegisterDTO;
import run.nya.petbbs.model.entity.SysUser;
import run.nya.petbbs.model.vo.ProfileVO;

/**
 * 用户接口
 *
 * 2021/02/18
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 用户登录
     *
     * @param  dto
     * @return JWT Token
     */
    String executeLogin(LoginDTO dto);

    /**
     * 用户注册
     *
     * @param  dto
     * @return SysUser
     */
    SysUser executeRegister(RegisterDTO dto);

    /**
     * 获取用户信息
     *
     * @param  username
     * @return SysUser
     */
    SysUser getUserByUsername(String username);

    /**
     * 获取用户信息
     *
     * @param  id 用户ID
     * @return
     */
    ProfileVO getUserProfile(String id);

    /**
     * 重新发送激活码
     *
     * @param name
     * @param email
     */
    void reActive(String name, String email);

    /**
     * 修改密码
     *
     * @param id
     * @param oldPass
     * @param newPass
     * @return
     */
    public boolean changePassword(String id, String oldPass, String newPass);

}
