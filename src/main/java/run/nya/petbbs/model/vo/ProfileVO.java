package run.nya.petbbs.model.vo;

import lombok.Data;

/**
 * 用户数据视图层
 *
 * 2021/03/01
 */
@Data
public class ProfileVO {

    private String id;

    private String username;

    private String alias;

    private String avatar;

//    private Integer followCount;

//    private Integer followerCount;

    private Integer postCount;

    private Integer sections;

    private Integer commentCount;

}
