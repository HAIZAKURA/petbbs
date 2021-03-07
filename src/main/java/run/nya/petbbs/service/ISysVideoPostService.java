package run.nya.petbbs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import run.nya.petbbs.model.dto.CreateVideoDTO;
import run.nya.petbbs.model.entity.SysUser;
import run.nya.petbbs.model.entity.SysVideoPost;
import run.nya.petbbs.model.vo.VideoPostVO;

import java.util.List;
import java.util.Map;

/**
 * 视频话题接口
 *
 * 2021/03/07
 */
public interface ISysVideoPostService extends IService<SysVideoPost> {

    /**
     * 获取视频话题列表
     *
     * @param  page
     * @param  tab
     * @param  tagId
     * @return Page
     */
    Page<VideoPostVO> getList(Page<VideoPostVO> page, String tab, String tagId);

    /**
     * 查看视频详情
     *
     * @param  id
     * @return Map
     */
    Map<String, Object> viewVideo(String id);

    /**
     * 创建视频
     *
     * @param  dto
     * @param  user
     * @return SysVideoPost
     */
    SysVideoPost create(CreateVideoDTO dto, SysUser user);

    /**
     * 关键词检索
     *
     * @param  page
     * @param  keyword
     * @return Page
     */
    Page<VideoPostVO> searchByKey(Page<VideoPostVO> page, String keyword);

    /**
     * 随机推荐
     * 10篇
     *
     * @param  id
     * @return List
     */
    List<VideoPostVO> getRecommend(String id);

    /**
     * 用户主页查询用户的视频
     *
     * @param  page
     * @param  userId
     * @return Page
     */
    Page<SysVideoPost> selectPostByUserId(Page<SysVideoPost> page, String userId);

}
