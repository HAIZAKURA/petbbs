package run.nya.petbbs.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.nya.petbbs.mapper.SysSectionMapper;
import run.nya.petbbs.model.dto.SectionDTO;
import run.nya.petbbs.model.entity.SysSection;
import run.nya.petbbs.model.vo.SectionVO;
import run.nya.petbbs.service.ISysSectionService;

/**
 * 话题专栏实现类
 *
 * 2021/02/27
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ISysSectionServiceImpl extends ServiceImpl<SysSectionMapper, SysSection> implements ISysSectionService {

    /**
     * 添加专栏
     *
     * @param  dto
     * @return SysSection
     */
    @Override
    public SysSection addSection(SectionDTO dto, String userId) {
        SysSection sysSection = SysSection.builder()
                .userId(userId)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .thumbnail(dto.getThumbnail())
                .build();
        baseMapper.insert(sysSection);
        return sysSection;
    }

    /**
     * 查询专栏列表
     *
     * @param  sectionVOPage
     * @return Page
     */
    public Page<SectionVO> getList(Page<SectionVO> sectionVOPage) {
        return this.baseMapper.selectPageVo(sectionVOPage);
    }

}
