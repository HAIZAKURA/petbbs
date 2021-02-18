package run.nya.petbbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import run.nya.petbbs.model.entity.SysUser;

/**
 * 用户
 */
@Mapper
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
}
