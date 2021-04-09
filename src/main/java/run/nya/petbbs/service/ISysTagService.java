package run.nya.petbbs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import run.nya.petbbs.model.entity.SysPost;
import run.nya.petbbs.model.entity.SysTag;

import java.util.List;

/**
 * 标签接口
 *
 * 2021/02/27
 */
public interface ISysTagService extends IService<SysTag> {

    /**
     * 添加标签
     *
     * @param  tags
     * @return List
     */
    List<SysTag> addTags(List<String> tags);

    /**
     * 获取标签关联话题
     *
     * @param  postPage
     * @param  id
     * @return Page
     */
    Page<SysPost> selectPostsByTagId(Page<SysPost> postPage, String id);

}
