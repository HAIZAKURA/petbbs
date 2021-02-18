package run.nya.petbbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import run.nya.petbbs.model.entity.SysPermission;

/**
 * 权限
 */
@Mapper
@Repository
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
}
