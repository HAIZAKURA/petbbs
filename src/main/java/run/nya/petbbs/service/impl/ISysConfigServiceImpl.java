package run.nya.petbbs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.nya.petbbs.mapper.SysConfigMapper;
import run.nya.petbbs.model.entity.SysConfig;
import run.nya.petbbs.service.ISysConfigService;

import java.util.HashMap;
import java.util.Map;

/**
 * 设置接口实现类
 *
 * 2021/03/06
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ISysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    @Override
    public Map<String, Object> getMailConfig() {
        Map<String, Object> map = new HashMap<>(16);
        return map;
    }

}
