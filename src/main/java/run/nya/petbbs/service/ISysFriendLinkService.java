package run.nya.petbbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import run.nya.petbbs.model.dto.FriendLinkDTO;
import run.nya.petbbs.model.entity.SysFriendLink;

/**
 * 友链接口
 *
 * 2021/02/26
 */
public interface ISysFriendLinkService extends IService<SysFriendLink> {

    /**
     * 添加友链
     *
     * @param  dto
     * @return SysFriendLink
     */
    SysFriendLink addLink(FriendLinkDTO dto);

}
