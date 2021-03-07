package run.nya.petbbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import run.nya.petbbs.model.entity.SysCollect;

/**
 * 收藏
 *
 * 2021/03/07
 */
@Mapper
@Repository
public interface SysCollectMapper extends BaseMapper<SysCollect> {
}
