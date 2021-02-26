package run.nya.petbbs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.nya.petbbs.mapper.SysBillboardMapper;
import run.nya.petbbs.model.dto.BillboardDTO;
import run.nya.petbbs.model.entity.SysBillboard;
import run.nya.petbbs.service.ISysBillboardService;

/**
 * 公告实现类
 *
 * 2021/02/26
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ISysBillboardServiceImpl extends ServiceImpl<SysBillboardMapper, SysBillboard> implements ISysBillboardService {

    /**
     * 添加公告
     *
     * @param  dto
     * @return SysBillboard
     */
    @Override
    public SysBillboard addBillboard(BillboardDTO dto) {
        SysBillboard sysBillboard = SysBillboard.builder()
                .content(dto.getContent())
                .build();
        baseMapper.insert(sysBillboard);
        return sysBillboard;
    }

}
