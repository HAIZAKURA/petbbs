package run.nya.petbbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import run.nya.petbbs.model.entity.SysFriendLink;

/**
 * 友链
 *
 * 2021/02/26
 */
@Mapper
@Repository
public interface SysFriendLinkMapper extends BaseMapper<SysFriendLink> {
}
