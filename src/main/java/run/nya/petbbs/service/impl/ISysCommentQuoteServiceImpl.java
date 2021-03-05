package run.nya.petbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.nya.petbbs.mapper.SysCommentQuoteMapper;
import run.nya.petbbs.model.entity.SysCommentQuote;
import run.nya.petbbs.service.ISysCommentQuoteService;

import java.util.List;

/**
 * 评论-引用接口实现类
 *
 * 2021/03/05
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ISysCommentQuoteServiceImpl extends ServiceImpl<SysCommentQuoteMapper, SysCommentQuote> implements ISysCommentQuoteService {

    /**
     * 获取评论ID关联记录
     *
     * @param  commentId
     * @return List
     */
    @Override
    public List<SysCommentQuote> selectByCommentId(String commentId) {
        QueryWrapper<SysCommentQuote> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysCommentQuote::getCommentId, commentId);
        return baseMapper.selectList(wrapper);
    }

}
