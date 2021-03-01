package run.nya.petbbs.model.vo;

import lombok.Data;

/**
 * 用户数据视图层
 *
 * 2021/03/01
 */
@Data
public class ProfileVO {

    /**
     * 用户ID
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 别称
     */
    private String alias;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 关注数
     */
//    private Integer followCount;

    /**
     * 关注者数
     */
//    private Integer followerCount;

    /**
     * 文章数
     */
    private Integer postCount;

    /**
     * 专栏数
     */
    private Integer sections;

    /**
     * 评论数
     */
    private Integer commentCount;

}
