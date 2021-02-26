package run.nya.petbbs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.nya.petbbs.mapper.SysFriendLinkMapper;
import run.nya.petbbs.model.dto.FriendLinkDTO;
import run.nya.petbbs.model.entity.SysFriendLink;
import run.nya.petbbs.service.ISysFriendLinkService;

/**
 * 友链实现类
 *
 * 2021/02/26
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ISysFriendLinkServiceImpl extends ServiceImpl<SysFriendLinkMapper, SysFriendLink> implements ISysFriendLinkService {

    /**
     * 添加友链
     *
     * @param  dto
     * @return SysFriendLink
     */
    @Override
    public SysFriendLink addLink(FriendLinkDTO dto) {
        SysFriendLink sysFriendLink = SysFriendLink.builder()
                .title(dto.getTitle())
                .link(dto.getLink())
                .icon(dto.getIcon())
                .build();
        baseMapper.insert(sysFriendLink);
        return sysFriendLink;
    }

}
