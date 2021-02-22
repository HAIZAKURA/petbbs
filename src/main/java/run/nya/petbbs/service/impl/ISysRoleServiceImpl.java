package run.nya.petbbs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.nya.petbbs.mapper.SysRoleMapper;
import run.nya.petbbs.model.entity.SysRole;
import run.nya.petbbs.service.ISysRoleService;

/**
 * 角色实现类
 *
 * 2021/02/19
 */
@Service
public class ISysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    /**
     * 获取角色
     *
     * @param  roleId
     * @return
     */
    @Override
    public SysRole getRoleById(Integer roleId) {
        return this.baseMapper.selectById(roleId);
    }

}
