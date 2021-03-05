package run.nya.petbbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import run.nya.petbbs.model.entity.SysCommentQuote;

/**
 * 评论-引用
 *
 * 2021/03/05
 */
@Mapper
@Repository
public interface SysCommentQuoteMapper extends BaseMapper<SysCommentQuote> {
}
