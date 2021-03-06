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
 * 设置实体类
 *
 * 2021/03/06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_config")
@Accessors(chain = true)
public class SysConfig implements Serializable {

    private static final long serialVersionUID = 2267895957300212531L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("item")
    private String item;

    @TableField("value")
    private String value;

    @TableField("remark")
    private String remark;

    /**
     * 重启
     */
    @Builder.Default
    @TableField("reboot")
    private Boolean reboot = false;

}
