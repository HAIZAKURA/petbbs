package run.nya.petbbs.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 照片实体类
 *
 * 2021/03/014
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_photo")
@Document(indexName = "photo", shards = 1, replicas = 1)
@Accessors(chain = true)
public class SysPhoto implements Serializable {

    private static final long serialVersionUID = 6839133021373287489L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    @NotBlank(message = "照片不可以为空")
    @TableField(value = "photo")
    private String photo;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    @NotBlank(message = "内容不可以为空")
    @TableField("content")
    private String content;

    @Field(type = FieldType.Keyword)
    @TableField("user_id")
    private String userId;

    @Field(type = FieldType.Integer)
    @TableField("comments")
    @Builder.Default
    private Integer comments = 0;

    @TableField("view")
    @Builder.Default
    @Field(type = FieldType.Integer)
    private Integer view = 0;

    @TableField("good")
    @Builder.Default
    @Field(type = FieldType.Integer)
    private Integer good = 0;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

}
