package run.nya.petbbs.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 友链实体类
 *
 * 2021/02/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_friend_link")
@Accessors(chain = true)
public class SysFriendLink implements Serializable {

    private static final long serialVersionUID = 1834129554361549520L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("title")
    private String title;

    @TableField("link")
    private String link;

    @TableField("icon")
    private String icon;

    /**
     * 状态
     */
    @Builder.Default
    @TableField("state")
    private Boolean state = true;

}
