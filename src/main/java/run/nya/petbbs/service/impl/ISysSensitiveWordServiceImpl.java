package run.nya.petbbs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.nya.petbbs.mapper.SysSensitiveWordMapper;
import run.nya.petbbs.model.entity.SysSensitiveWord;
import run.nya.petbbs.service.ISysSensitiveWordService;

import java.util.ArrayList;
import java.util.List;

/**
 * 敏感词接口实现类
 *
 * 2021/03/07
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ISysSensitiveWordServiceImpl extends ServiceImpl<SysSensitiveWordMapper, SysSensitiveWord> implements ISysSensitiveWordService {

    @Override
    public List<SysSensitiveWord> add(List<String> words) {
        List<SysSensitiveWord> wordList = new ArrayList<>();
        for (String word : words) {
            SysSensitiveWord sysSensitiveWord = SysSensitiveWord.builder().word(word).build();
            baseMapper.insert(sysSensitiveWord);
            wordList.add(sysSensitiveWord);
        }
        return wordList;
    }

}
