package run.nya.petbbs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import run.nya.petbbs.model.dto.CreatePostDTO;
import run.nya.petbbs.model.entity.SysPost;
import run.nya.petbbs.model.entity.SysSection;
import run.nya.petbbs.model.entity.SysTag;
import run.nya.petbbs.model.entity.SysUser;
import run.nya.petbbs.model.vo.PostVO;

import java.util.List;
import java.util.Map;

/**
 * 话题接口
 *
 * 2021/03/01
 */
public interface ISysPostService extends IService<SysPost> {

    /**
     * 获取首页话题列表
     *
     * @param  page
     * @param  tab
     * @return Page
     */
    Page<PostVO> getList(Page<PostVO> page, String tab, Integer sectionId, String tagId);


    /**
     * 查看话题详情
     *
     * @param  id
     * @return Map
     */
    Map<String, Object> viewPost(String id);

    /**
     * 查询当前作者其他话题
     * 10篇
     *
     * @param  userId
     * @param  postId
     * @return List
     */
    List<SysPost> selectAuthorOtherPost(String userId, String postId);

    /**
     * 用户主页查询用户的话题
     * 10篇
     *
     * @param  userId
     * @param  page
     * @return Page
     */
    Page<SysPost> selectPostByUserId(Page<SysPost> page, String userId);

    /**
     * 获取随机推荐
     * 10篇
     *
     * @param  id
     * @return List
     */
    List<SysPost> getRecommend(String id);

    /**
     * 发布
     *
     * @param  dto
     * @param  sysUser
     * @return SysPost
     */
    SysPost create(CreatePostDTO dto, SysUser sysUser);

    /**
     * 专栏检索
     *
     * @param  page
     * @param  section
     * @return Page
     */
//    Page<PostVO> selectBySection(Page<PostVO> page, SysSection section);

    /**
     * 关键字检索
     *
     * @param  keyword
     * @param  page
     * @return Page
     */
    Page<PostVO> searchByKey(Page<PostVO> page, String keyword);

}
