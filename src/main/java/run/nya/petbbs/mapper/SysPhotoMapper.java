package run.nya.petbbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import run.nya.petbbs.model.entity.SysPhoto;
import run.nya.petbbs.model.vo.PhotoVO;

/**
 * 照片
 *
 * 2021/03/014
 */
@Mapper
@Repository
public interface SysPhotoMapper extends BaseMapper<SysPhoto> {

    Page<PhotoVO> getList(@Param("page") Page<PhotoVO> page);

}
