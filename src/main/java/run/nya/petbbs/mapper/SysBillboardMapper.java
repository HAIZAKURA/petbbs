package run.nya.petbbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import run.nya.petbbs.model.entity.SysBillboard;

/**
 * 公告
 *
 * 2021/02/26
 */
@Mapper
@Repository
public interface SysBillboardMapper extends BaseMapper<SysBillboard> {
}
