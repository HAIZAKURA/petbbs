package run.nya.petbbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import run.nya.petbbs.model.entity.SysSection;
import run.nya.petbbs.model.vo.SectionVO;

/**
 * 话题专栏
 *
 * 2021/02/27
 */
@Mapper
@Repository
public interface SysSectionMapper extends BaseMapper<SysSection> {

    /**
     * 查询专栏列表
     *
     * @param page
     * @return
     */
    Page<SectionVO> selectPageVo(IPage<SectionVO> page);

}
