package run.nya.petbbs.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 七牛实体类
 */
@Data
@TableName("qiniu_config")
@Accessors(chain = true)
public class QiniuConfig implements Serializable {

    private static final long serialVersionUID = 438932949532066424L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("access_key")
    private String accessKey;

    @TableField("secret_key")
    private String secretKey;

    @TableField("bucket")
    private String bucket;

    @TableField("host")
    private String host;

}
