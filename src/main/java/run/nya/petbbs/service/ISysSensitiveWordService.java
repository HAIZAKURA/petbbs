package run.nya.petbbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import run.nya.petbbs.model.entity.SysSensitiveWord;

import java.util.List;

/**
 * 敏感词接口
 *
 * 2021/03/07
 */
public interface ISysSensitiveWordService extends IService<SysSensitiveWord> {

    List<SysSensitiveWord> add(List<String> words);

}
