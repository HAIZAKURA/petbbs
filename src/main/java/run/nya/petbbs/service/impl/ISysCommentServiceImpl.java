package run.nya.petbbs.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import run.nya.petbbs.mapper.SysCommentMapper;
import run.nya.petbbs.mapper.SysCommentQuoteMapper;
import run.nya.petbbs.mapper.SysPhotoMapper;
import run.nya.petbbs.mapper.SysPostMapper;
import run.nya.petbbs.model.dto.CreateCommentDTO;
import run.nya.petbbs.model.entity.*;
import run.nya.petbbs.model.vo.CommentVO;
import run.nya.petbbs.model.vo.QuoteVO;
import run.nya.petbbs.service.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论接口实现类
 *
 * 2021/03/05
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ISysCommentServiceImpl extends ServiceImpl<SysCommentMapper, SysComment> implements ISysCommentService {

    @Autowired
    private ISysPostService iSysPostService;

    @Autowired
    private ISysPhotoService iSysPhotoService;

    @Autowired
    private ISysCommentQuoteService iSysCommentQuoteService;

    @Autowired
    private ISysNotifyService iSysNotifyService;

    @Resource
    private SysPostMapper sysPostMapper;

    @Resource
    private SysPhotoMapper sysPhotoMapper;

    @Resource
    private SysCommentMapper sysCommentMapper;

    @Resource
    private SysCommentQuoteMapper sysCommentQuoteMapper;

    /**
     * 创建评论
     *
     * @param  dto
     * @param  sysUser
     * @return SysComment
     */
    @Override
    public SysComment create(CreateCommentDTO dto, SysUser sysUser) {
        SysComment comment = SysComment.builder()
                .postId(dto.getPostId())
                .content(dto.getContent())
                .userId(sysUser.getId())
                .createTime(new Date())
                .build();
        baseMapper.insert(comment);
        if (!ObjectUtils.isEmpty(dto.getQuoteId())) {
            SysCommentQuote commentQuote = SysCommentQuote.builder()
                    .quoteId(dto.getQuoteId())
                    .commentId(comment.getId())
                    .build();
            sysCommentQuoteMapper.insert(commentQuote);
            SysComment sysComment = baseMapper.selectById(dto.getQuoteId());
            iSysNotifyService.quoteNotify(sysComment.getUserId(), sysComment.getPostId(), comment.getId());
        }
        SysPost sysPost = iSysPostService.getById(dto.getPostId());
        Integer newComments = sysPost.getComments() + 1;
        sysPostMapper.updateById(sysPost.setComments(newComments));
        return comment;
    }

    /**
     * 创建评论
     *
     * @param  dto
     * @param  sysUser
     * @return SysComment
     */
    @Override
    public SysComment createPhoto(CreateCommentDTO dto, SysUser sysUser) {
        SysComment comment = SysComment.builder()
                .postId(dto.getPostId())
                .content(dto.getContent())
                .userId(sysUser.getId())
                .createTime(new Date())
                .build();
        baseMapper.insert(comment);
        if (!ObjectUtils.isEmpty(dto.getQuoteId())) {
            SysCommentQuote commentQuote = SysCommentQuote.builder()
                    .quoteId(dto.getQuoteId())
                    .commentId(comment.getId())
                    .build();
            sysCommentQuoteMapper.insert(commentQuote);
            SysComment sysComment = baseMapper.selectById(dto.getQuoteId());
            iSysNotifyService.quoteNotify(sysComment.getUserId(), sysComment.getPostId(), comment.getId());
        }
        SysPhoto sysPhoto = iSysPhotoService.getById(dto.getPostId());
        Integer newComments = sysPhoto.getComments() + 1;
        sysPhotoMapper.updateById(sysPhoto.setComments(newComments));
        return comment;
    }

    /**
     * 获取评论列表
     *
     * @param  page
     * @param  postId
     * @return Page
     */
    @Override
    public Page<CommentVO> getList(Page<CommentVO> page, String postId) {
        Page<CommentVO> iPage = baseMapper.selectListAndPage(page, postId);
        iPage.getRecords().forEach(comment -> {
            List<SysCommentQuote> commentQuotes = iSysCommentQuoteService.selectByCommentId(comment.getId());
            if (!commentQuotes.isEmpty()) {
                List<String> quoteIds = commentQuotes.stream().map(SysCommentQuote::getQuoteId).collect(Collectors.toList());
                List<QuoteVO> quotes = new ArrayList<>();
                quoteIds.forEach(id -> {
                    quotes.add(sysCommentMapper.selectQuotesById(id));
                });
                comment.setQuotes(quotes);
            }
        });
        return iPage;
    }

}
