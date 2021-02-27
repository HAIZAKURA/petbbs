package run.nya.petbbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import run.nya.petbbs.model.dto.SectionDTO;
import run.nya.petbbs.model.entity.SysSection;

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
    SysSection addSection(SectionDTO dto);

}
