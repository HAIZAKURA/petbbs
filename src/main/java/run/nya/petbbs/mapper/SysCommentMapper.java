package run.nya.petbbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import run.nya.petbbs.model.entity.SysComment;
import run.nya.petbbs.model.vo.CommentVO;
import run.nya.petbbs.model.vo.QuoteVO;

import java.util.List;

/**
 * 评论
 *
 * 2021/03/05
 */
@Mapper
@Repository
public interface SysCommentMapper extends BaseMapper<SysComment> {

    Page<CommentVO> selectListAndPage(@Param("page") Page<CommentVO> page, @Param("postId") String postId);

    List<QuoteVO> selectQuotesByIds(@Param("commentIds") List<String> commentIds);


}
