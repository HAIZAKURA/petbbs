package run.nya.petbbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import run.nya.petbbs.model.entity.SysNotify;

/**
 * 通知
 *
 * 2021/03/08
 */
@Mapper
@Repository
public interface SysNotifyMapper extends BaseMapper<SysNotify> {
}
