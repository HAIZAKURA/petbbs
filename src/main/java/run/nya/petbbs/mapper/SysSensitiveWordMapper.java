package run.nya.petbbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import run.nya.petbbs.model.entity.SysSensitiveWord;

import java.util.List;

/**
 * 敏感词
 *
 * 2021/03/07
 */
@Mapper
@Repository
public interface SysSensitiveWordMapper extends BaseMapper<SysSensitiveWord> {

    List<String> getWords();

}
