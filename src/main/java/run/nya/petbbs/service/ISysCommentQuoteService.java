package run.nya.petbbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import run.nya.petbbs.model.entity.SysCommentQuote;

import java.util.List;

/**
 * 评论-引用接口
 *
 * 2021/03/05
 */
public interface ISysCommentQuoteService extends IService<SysCommentQuote> {

    /**
     * 获取评论ID关联记录
     *
     * @param  commentId
     * @return List
     */
    List<SysCommentQuote> selectByCommentId(String commentId);

}
