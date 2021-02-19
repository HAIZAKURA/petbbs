package run.nya.petbbs.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 权限实体类
 *
 * 2021/02/18
 */
@Data
@TableName("sys_permission")
@Accessors(chain = true)
public class SysPermission implements Serializable {

    private static final long serialVersionUID = -8339384636401104783L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("`value`")
    private String value;

    @TableField("remark")
    private String remark;

    @TableField("pid")
    private Integer pid;

}
