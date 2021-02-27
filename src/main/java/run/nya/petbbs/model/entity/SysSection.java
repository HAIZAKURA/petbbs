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
 * 话题专栏实体类
 *
 * 2021/02/27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_section")
@Accessors(chain = true)
public class SysSection implements Serializable {

    private static final long serialVersionUID = 5397482956270250492L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private String userId;

    @TableField("title")
    private String title;

    @TableField("description")
    private String description;

    @TableField("thumbnail")
    private String thumbnail;

}
