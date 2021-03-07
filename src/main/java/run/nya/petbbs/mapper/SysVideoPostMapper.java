package run.nya.petbbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import run.nya.petbbs.model.entity.SysVideoPost;
import run.nya.petbbs.model.vo.VideoPostVO;

import java.util.List;

/**
 * 视频话题
 *
 * 2021/03/07
 */
@Mapper
@Repository
public interface SysVideoPostMapper extends BaseMapper<SysVideoPost> {

    Page<VideoPostVO> selectListAndPage(@Param("page") Page<VideoPostVO> page, @Param("tab") String tab, @Param("tagId") String tagId);

    List<VideoPostVO> selectRecommend(@Param("id") String id);

    Page<VideoPostVO> searchByKey(@Param("page") Page<VideoPostVO> page, @Param("keyword") String keyword);

}
