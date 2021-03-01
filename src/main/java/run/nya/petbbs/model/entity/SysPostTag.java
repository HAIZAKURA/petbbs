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
 * 话题-标签 实体类
 *
 * 2021/03/01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_post_tag")
@Accessors(chain = true)
public class SysPostTag implements Serializable {

    private static final long serialVersionUID = 3471849175953197745L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("tag_id")
    private String tagId;

    @TableField("post_id")
    private String postId;

}
