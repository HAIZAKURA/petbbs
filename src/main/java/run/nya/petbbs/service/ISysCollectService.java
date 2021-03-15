package run.nya.petbbs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import run.nya.petbbs.model.entity.SysCollect;
import run.nya.petbbs.model.vo.CollectVO;
import run.nya.petbbs.model.vo.PostVO;

/**
 * 收藏接口
 *
 * 2021/03/07
 */
public interface ISysCollectService extends IService<SysCollect> {

    /**
     * 收藏
     *
     * @param  postId
     * @param  userId
     * @return SysCollect
     */
    SysCollect collect(String postId, String userId);

    /**
     * 获取收藏列表
     *
     * @param page
     * @param userId
     * @return
     */
    Page<CollectVO> getCollects(Page<CollectVO> page, String userId);

}
