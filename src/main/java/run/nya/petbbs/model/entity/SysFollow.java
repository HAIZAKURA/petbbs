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
 * 用户关注实体类
 *
 * 2021/03/07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_follow")
@Accessors(chain = true)
public class SysFollow implements Serializable {

    private static final long serialVersionUID = -8542651329899348196L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private String userId;

    @TableField("follow_id")
    private String followId;

}
