package run.nya.petbbs.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告实体类
 *
 * 2021/02/26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_billboard")
@Accessors(chain = true)
public class SysBillboard implements Serializable {

    private static final long serialVersionUID = -74816616068644903L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("content")
    private String content;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 展示
     */
    @Builder.Default
    @TableField("state")
    private Boolean state = true;

}
