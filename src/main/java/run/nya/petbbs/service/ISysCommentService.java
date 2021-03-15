package run.nya.petbbs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import run.nya.petbbs.model.dto.CreateCommentDTO;
import run.nya.petbbs.model.entity.SysComment;
import run.nya.petbbs.model.entity.SysUser;
import run.nya.petbbs.model.vo.CommentVO;

/**
 * 评论接口
 *
 * 2021/03/05
 */
public interface ISysCommentService extends IService<SysComment> {

    /**
     * 创建评论
     *
     * @param  dto
     * @param  sysUser
     * @return SysComment
     */
    SysComment create(CreateCommentDTO dto, SysUser sysUser);

    /**
     * 创建评论
     *
     * @param  dto
     * @param  sysUser
     * @return SysComment
     */
    SysComment createPhoto(CreateCommentDTO dto, SysUser sysUser);

    /**
     * 获取评论列表
     *
     * @param  page
     * @param  postId
     * @return Page
     */
    Page<CommentVO> getList(Page<CommentVO> page, String postId);

}
