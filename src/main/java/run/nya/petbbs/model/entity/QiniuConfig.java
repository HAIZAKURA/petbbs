package run.nya.petbbs.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 七牛实体类
 *
 * 2021/02/26
 */
@Data
@Builder
@TableName("qiniu_config")
@Accessors(chain = true)
public class QiniuConfig implements Serializable {

    private static final long serialVersionUID = 438932949532066424L;

    @TableField("name")
    private String name;

    @TableField("accesskey")
    private String accesskey;

    @TableField("secretkey")
    private String secretkey;

    @TableField("bucket")
    private String bucket;

    @TableField("host")
    private String host;

    @TableField("usessl")
    private Boolean usessl;

}
