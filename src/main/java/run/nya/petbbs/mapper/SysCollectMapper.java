package run.nya.petbbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import run.nya.petbbs.model.entity.SysCollect;
import run.nya.petbbs.model.vo.CollectVO;

/**
 * 收藏
 *
 * 2021/03/07
 */
@Mapper
@Repository
public interface SysCollectMapper extends BaseMapper<SysCollect> {

    /**
     * 获取收藏列表
     * @param  page
     * @param  userId
     * @return
     */
    Page<CollectVO> getCollects(@Param("page")Page<CollectVO> page, @Param("userId") String userId);

}
