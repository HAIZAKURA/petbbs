package run.nya.petbbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import run.nya.petbbs.model.entity.SysCollect;

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

}
