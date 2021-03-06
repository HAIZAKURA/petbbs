package run.nya.petbbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import run.nya.petbbs.model.entity.SysConfig;

/**
 * 设置
 *
 * 2021/03/06
 */
@Mapper
@Repository
public interface SysConfigMapper extends BaseMapper<SysConfig> {
}
