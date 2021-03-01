package run.nya.petbbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import run.nya.petbbs.model.entity.SysPostTag;
import run.nya.petbbs.model.entity.SysTag;

import java.util.List;
import java.util.Set;

/**
 * 话题-标签接口
 *
 * 2021/03/01
 */
public interface ISysPostTagService extends IService<SysPostTag> {

    /**
     * 获取Topic Tag 关联记录
     *
     * @param  postId
     * @return List
     */
    List<SysPostTag> selectByPostId(String postId);

    /**
     * 获取标签换脸话题ID集合
     *
     * @param  id
     * @return Set
     */
    Set<String> selectPostIdsByTagId(String id);

    /**
     * 创建中间关系
     *
     * @param id
     * @param tags
     * @return
     */
    void createPostTag(String id, List<SysTag> tags);

}
