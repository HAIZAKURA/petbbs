package run.nya.petbbs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.nya.petbbs.mapper.SysTagMapper;
import run.nya.petbbs.model.dto.TagDTO;
import run.nya.petbbs.model.entity.SysTag;
import run.nya.petbbs.service.ISysTagService;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ISysTagServiceImpl extends ServiceImpl<SysTagMapper, SysTag> implements ISysTagService {

    /**
     * 添加标签
     *
     * @param  dto
     * @return SysTag
     */
    @Override
    public SysTag addTag(TagDTO dto) {
        SysTag sysTag = SysTag.builder()
                .name(dto.getName())
                .build();
        baseMapper.insert(sysTag);
        return sysTag;
    }

}
