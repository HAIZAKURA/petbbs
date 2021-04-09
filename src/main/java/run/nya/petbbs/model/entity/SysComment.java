package run.nya.petbbs.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 评论实体类
 *
 * 2021/03/5
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_comment")
@Document(indexName = "comment", shards = 1, replicas = 1)
public class SysComment implements Serializable {

    private static final long serialVersionUID = 4358778957167858397L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @Field(type = FieldType.Keyword)
    @TableField("post_id")
    private String postId;

    @Field(type = FieldType.Keyword)
    @TableField("user_id")
    private String userId;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    @NotBlank(message = "内容不可以为空")
    @TableField("`content`")
    private String content;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

}
