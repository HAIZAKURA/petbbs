package run.nya.petbbs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import run.nya.petbbs.model.entity.SysPost;
import run.nya.petbbs.model.entity.SysSection;
import run.nya.petbbs.model.vo.PostVO;

import java.util.List;

/**
 * 话题
 *
 * 2021/03/01
 */
@Mapper
@Repository
public interface SysPostMapper extends BaseMapper<SysPost> {

    /**
     * 分页查询首页话题列表
     *
     * @param page
     * @param tab
     * @return
     */
    Page<PostVO> selectListAndPage(@Param("page") Page<PostVO> page, @Param("tab") String tab,
                                   @Param("sectionId") Integer sectionId, @Param("tagId") String tagId);

    /**
     * 获取详情页推荐
     *
     * @param id
     * @return
     */
    List<SysPost> selectRecommend(@Param("id") String id);

    /**
     * 专栏检索
     *
     * @param page
     * @param section
     * @return
     */
    Page<PostVO> selectBySection(@Param("page") Page<PostVO> page, @Param("section") SysSection section);

    /**
     * 全文检索
     *
     * @param page
     * @param keyword
     * @return
     */
    Page<PostVO> searchByKey(@Param("page") Page<PostVO> page, @Param("keyword") String keyword);

    /**
     * 随机话题
     *
     * @return SysPost
     */
    SysPost randomPost();

}
