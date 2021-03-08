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

/**
 * 通知实体类
 *
 * 2021/03/08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_notify")
@Accessors(chain = true)
public class SysNotify implements Serializable {

    private static final long serialVersionUID = -5025040221039761125L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("user_id")
    private String userId;

    @TableField("content")
    private String content;

    @TableField("remark")
    private String remark;

    @Builder.Default
    @TableField("state")
    private Boolean state = true;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

}
