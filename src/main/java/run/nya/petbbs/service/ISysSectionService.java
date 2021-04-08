package run.nya.petbbs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import run.nya.petbbs.model.dto.SectionDTO;
import run.nya.petbbs.model.entity.SysSection;
import run.nya.petbbs.model.vo.SectionVO;

/**
 * 话题专栏接口
 *
 * 2021/02/27
 */
public interface ISysSectionService extends IService<SysSection> {

    /**
     * 添加专栏
     *
     * @param  dto
     * @return SysSection
     */
    SysSection addSection(SectionDTO dto, String userId);

    /**
     * 查询专栏列表
     *
     * @param  sectionVOPage
     * @return Page
     */
    Page<SectionVO> getList(Page<SectionVO> sectionVOPage);

}
