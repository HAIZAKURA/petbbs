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
 * 评论-引用实体类
 *
 * 2021/03/05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_comment_quote")
@Accessors(chain = true)
public class SysCommentQuote implements Serializable {

    private static final long serialVersionUID = 586726717725468045L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("comment_id")
    private String commentId;

    @TableField("quote_id")
    private String quoteId;

}
