package run.nya.petbbs.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.nya.petbbs.model.entity.SysTag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 话题视图层
 *
 * 2021/03/01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostVO implements Serializable {

    private static final long serialVersionUID = -125190698273097621L;

    /**
     * 文章ID
     */
    private String id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 用户昵称
     */
    private String alias;
    /**
     * 账号
     */
    private String username;
    /**
     * 标题
     */
    private String title;
    /**
     * 评论统计
     */
    private Integer comments;
    /**
     * 置顶
     */
    private Boolean top;
    /**
     * 加精
     */
    private Boolean essence;
    /**
     * 收藏次數
     */
    private Integer collects;
    /**
     * 话题关联标签
     */
    private List<SysTag> tags;
    /**
     * 浏览量
     */
    private Integer view;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
}
