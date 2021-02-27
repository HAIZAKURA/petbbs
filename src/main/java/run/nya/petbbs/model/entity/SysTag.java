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
 * 标签实体类
 *
 * 2021/02/27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_tag")
@Accessors(chain = true)
public class SysTag implements Serializable {

    private static final long serialVersionUID = 9022915074845862648L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @TableField("name")
    private String name;

    @Builder.Default
    @TableField("topic_count")
    private Integer topicCount = 0;

}
