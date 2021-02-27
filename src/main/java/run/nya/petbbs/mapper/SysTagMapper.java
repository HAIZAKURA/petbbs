package run.nya.petbbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import run.nya.petbbs.model.entity.SysTag;

/**
 * 标签
 *
 * 2021/02/27
 */
@Mapper
@Repository
public interface SysTagMapper extends BaseMapper<SysTag> {
}
