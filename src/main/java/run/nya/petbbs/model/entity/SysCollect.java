package run.nya.petbbs.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**\
 * 收藏实体类
 *
 * 2021/03/07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_collect")
@Accessors(chain = true)
public class SysCollect implements Serializable {

    private static final long serialVersionUID = -5371049929082346105L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("post_id")
    private String postId;

    @TableField("userId")
    private String userId;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

}
