package run.nya.petbbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import run.nya.petbbs.model.dto.TagDTO;
import run.nya.petbbs.model.entity.SysTag;

/**
 * 标签接口
 *
 * 2021/02/27
 */
public interface ISysTagService extends IService<SysTag> {

    /**
     * 添加标签
     *
     * @param  dto
     * @return SysTag
     */
    SysTag addTag(TagDTO dto);

}
