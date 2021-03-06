package run.nya.petbbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import run.nya.petbbs.model.entity.SysConfig;

import java.util.Map;

/**
 * 设置接口
 *
 * 2021/03/06
 */
public interface ISysConfigService extends IService<SysConfig> {

    Map<String, Object> getMailConfig();

}
