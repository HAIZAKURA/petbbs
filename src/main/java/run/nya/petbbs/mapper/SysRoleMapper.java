package run.nya.petbbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import run.nya.petbbs.model.entity.SysRole;

/**
 * 角色
 */
@Mapper
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {
}
