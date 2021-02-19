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
    String excuteLogin(LoginDTO dto);

    /**
     * 用户注册
     *
     * @param  dto
     * @return 注册对象
     */
    SysUser excuteLogin(RegisterDTO dto);

    /**
     * 获取用户信息
     *
     * @param  username
     * @return dbUser
     */
    SysUser getUserByUsername(String username);

    /**
     * 获取用户信息
     *
     * @param  id 用户ID
     * @return
     */
    ProfileVO getUserProfile(String id);

}
