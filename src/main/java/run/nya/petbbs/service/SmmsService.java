package run.nya.petbbs.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.nya.petbbs.entity.SmmsConfig;
import run.nya.petbbs.mapper.SmmsMapper;

@Service
@Transactional
public class SmmsService {

    @Autowired(required = false)
    private SmmsMapper smmsMapper;

    public String getSmmsToken() {
        QueryWrapper<SmmsConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("smms_item", "token");
        return smmsMapper.selectOne(queryWrapper).getSmmsValue();
    }

    public Integer setSmmsToken(String token) {
        UpdateWrapper<SmmsConfig> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("smms_item", "token");
        updateWrapper.set("smms_value", token);
        return smmsMapper.update(new SmmsConfig(), updateWrapper);
    }
}
