package run.nya.petbbs.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 *
 * 2021/02/18
 */
@Data
@Builder
@TableName("sys_user")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 745856566768973331L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("username")
    private String username;

    @TableField("alias")
    private String alias;

    @JsonIgnore
    @TableField("password")
    private String password;

    @Builder.Default
    @TableField("avatar")
    private String avatar = "https://cdn.jsdelivr.net/gh/HAIZAKURA/cdn@1.4.2/img/avatar/17_1_一姫2.png";

    @TableField("email")
    private String email;

    @TableField("mobile")
    private String mobile;

    @TableField("score")
    private Integer score;

    @JsonIgnore
    @TableField("token")
    private String token;

    @Builder.Default
    @TableField("bio")
    private String bio = "这里什么都没有哦";

    /**
     * 激活
     */
    @Builder.Default
    @TableField("active")
    private Boolean active = false;

    /**
     * 状态
     */
    @Builder.Default
    @TableField("status")
    private Boolean status = true;

    @TableField("role_id")
    private Integer roleId;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "modify_time", fill = FieldFill.INSERT_UPDATE)
    private Date modifyTime;

}
