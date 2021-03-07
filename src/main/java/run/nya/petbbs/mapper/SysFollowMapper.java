package run.nya.petbbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import run.nya.petbbs.model.entity.SysFollow;
import run.nya.petbbs.model.vo.FollowVO;

/**
 * 用户关注
 *
 * 2021/03/07
 */
@Mapper
@Repository
public interface SysFollowMapper extends BaseMapper<SysFollow> {

    /**
     * 获取粉丝
     *
     * @param  page
     * @param  userId
     * @return
     */
    Page<FollowVO> getFansPage(@Param("page") Page<FollowVO> page, @Param("userId") String userId);

    /**
     * 获取关注
     * @param page
     * @param userId
     * @return
     */
    Page<FollowVO> getFollowPage(@Param("page") Page<FollowVO> page, @Param("userId") String userId);


}
