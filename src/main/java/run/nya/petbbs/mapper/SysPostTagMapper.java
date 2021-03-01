package run.nya.petbbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import run.nya.petbbs.model.entity.SysPostTag;

import java.util.Set;

/**
 * 话题-标签
 *
 * 2021/03/01
 */
@Mapper
@Repository
public interface SysPostTagMapper extends BaseMapper<SysPostTag> {

    /**
     * 根据标签获取话题ID集合
     *
     * @param id
     * @return
     */
    Set<String> getPostIdsByTagId(@Param("id") String id);

}
