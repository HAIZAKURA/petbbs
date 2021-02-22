package run.nya.petbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import run.nya.petbbs.mapper.QiniuConfigMapper;
import run.nya.petbbs.model.dto.QiniuDTO;
import run.nya.petbbs.model.entity.QiniuConfig;
import run.nya.petbbs.service.IQiniuConfigService;

/**
 * 七牛实现类
 *
 * 2021/02/22
 */
@Service
public class IQiniuConfigServiceImpl extends ServiceImpl<QiniuConfigMapper, QiniuConfig> implements IQiniuConfigService {

    /**
     * 获取七牛配置
     *
     * @return
     */
    @Override
    public QiniuConfig getQiniuConfig() {
        LambdaQueryWrapper<QiniuConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(QiniuConfig::getName, "config");
        return baseMapper.selectOne(wrapper);
    }

    /**
     * 修改七牛配置
     *
     * @param  dto
     * @return
     */
    @Override
    public Integer updateQiniuConfig(QiniuDTO dto) {
        QiniuConfig qiniuConfig = QiniuConfig.builder()
                .name("config")
                .accesskey(dto.getAccesskey())
                .secretkey(dto.getSecretkey())
                .bucket(dto.getBucket())
                .host(dto.getHost())
                .usessl(dto.getUsessl())
                .build();
        LambdaQueryWrapper<QiniuConfig> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(QiniuConfig::getName, "config");
        return baseMapper.update(qiniuConfig, wrapper);
    }

}
