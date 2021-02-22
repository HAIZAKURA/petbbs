package run.nya.petbbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import run.nya.petbbs.model.dto.QiniuDTO;
import run.nya.petbbs.model.entity.QiniuConfig;

/**
 * 七牛接口
 *
 * 2021/02/22
 */
public interface IQiniuConfigService extends IService<QiniuConfig> {

    /**
     * 获取七牛配置
     *
     * @return
     */
    QiniuConfig getQiniuConfig();

    /**
     * 修改七牛配置
     *
     * @param  dto
     * @return
     */
    Integer updateQiniuConfig(QiniuDTO dto);

}
