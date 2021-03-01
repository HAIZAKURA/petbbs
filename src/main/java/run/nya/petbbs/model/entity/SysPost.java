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
 * 话题实体类
 *
 * 2021/03/01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_post")
@Document(indexName = "topic", shards = 1, replicas = 1)
public class SysPost implements Serializable {

    private static final long serialVersionUID = -1080216882051222464L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    @NotBlank(message = "标题不可以为空")
    @TableField(value = "title")
    private String title;

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

    @Field(type = FieldType.Integer)
    @TableField("section_id")
    @Builder.Default
    private Integer sectionId = 0;

    @Field(type = FieldType.Boolean)
    @TableField("top")
    @Builder.Default
    private Boolean top = false;

    @Field(type = FieldType.Boolean)
    @TableField("essence")
    @Builder.Default
    private Boolean essence = false;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    @TableField(value = "modify_time", fill = FieldFill.UPDATE)
    private Date modifyTime;

}
