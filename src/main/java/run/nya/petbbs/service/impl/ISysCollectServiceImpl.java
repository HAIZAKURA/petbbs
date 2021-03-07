package run.nya.petbbs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.nya.petbbs.mapper.SysCollectMapper;
import run.nya.petbbs.model.entity.SysCollect;
import run.nya.petbbs.service.ISysCollectService;

import java.util.Date;

/**
 * 收藏接口实现类
 *
 * 2021/03/07
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ISysCollectServiceImpl extends ServiceImpl<SysCollectMapper, SysCollect> implements ISysCollectService {

    /**
     * 收藏
     *
     * @param  postId
     * @param  userId
     * @return SysCollect
     */
    @Override
    public SysCollect collect(String postId, String userId) {
        SysCollect sysCollect = SysCollect.builder()
                .postId(postId)
                .userId(userId)
                .createTime(new Date())
                .build();
        baseMapper.insert(sysCollect);
        return sysCollect;
    }

}
