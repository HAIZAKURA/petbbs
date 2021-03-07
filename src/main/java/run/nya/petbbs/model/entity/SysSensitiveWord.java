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
 * 敏感词实体类
 *
 * 2021/03/07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("sys_sensitive_word")
@Accessors(chain = true)
public class SysSensitiveWord implements Serializable {

    private static final long serialVersionUID = -6564851170777504618L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("word")
    private String word;

}
