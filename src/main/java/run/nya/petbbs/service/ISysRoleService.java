package run.nya.petbbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import run.nya.petbbs.model.entity.SysRole;

public interface ISysRoleService extends IService<SysRole> {

    /**
     * 获取角色
     * @param  roleId
     * @return
     */
    SysRole getRoleById(Integer roleId);

}
