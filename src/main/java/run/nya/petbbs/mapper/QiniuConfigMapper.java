package run.nya.petbbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import run.nya.petbbs.model.entity.QiniuConfig;

/**
 * 七牛
 *
 * 2021/02/22
 */
@Mapper
@Repository
public interface QiniuConfigMapper extends BaseMapper<QiniuConfig> {
}
