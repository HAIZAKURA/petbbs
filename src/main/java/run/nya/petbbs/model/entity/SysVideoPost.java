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
 * 视频话题实体类
 *
 * 2021/03/07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_video_post")
@Document(indexName = "video_post", shards = 1, replicas = 1)
@Accessors(chain = true)
public class SysVideoPost implements Serializable {

    private static final long serialVersionUID = -4428748639465631300L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    @NotBlank(message = "标题不可以为空")
    @TableField(value = "title")
    private String title;

    @NotBlank(message = "封面不可以为空")
    @TableField(value = "cover")
    private String cover;

    @NotBlank(message = "视频不可以为空")
    @TableField(value = "video")
    private String video;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    @NotBlank(message = "内容不可以为空")
    @TableField("`content`")
    private String content;

    @Field(type = FieldType.Keyword)
    @TableField("user_id")
    private String userId;

    @Field(type = FieldType.Integer)
    @TableField("comments")
    @Builder.Default
    private Integer comments = 0;

    @TableField("collects")
    @Builder.Default
    @Field(type = FieldType.Integer)
    private Integer collects = 0;

    @TableField("view")
    @Builder.Default
    @Field(type = FieldType.Integer)
    private Integer view = 0;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    @TableField(value = "modify_time", fill = FieldFill.UPDATE)
    private Date modifyTime;

}
