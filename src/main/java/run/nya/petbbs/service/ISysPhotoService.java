package run.nya.petbbs.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import run.nya.petbbs.model.dto.CreatePhotoDTO;
import run.nya.petbbs.model.entity.SysPhoto;
import run.nya.petbbs.model.entity.SysUser;
import run.nya.petbbs.model.vo.PhotoVO;

import java.util.Map;

/**
 * 照片接口
 *
 * 2021/03/14
 */
public interface ISysPhotoService extends IService<SysPhoto> {

    Page<PhotoVO> getList(Page<PhotoVO> page);

    Map<String, Object> viewPhoto(String id);

    SysPhoto create(CreatePhotoDTO dto, SysUser user);

}
