package run.nya.petbbs.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 七牛DTO
 *
 * 2021/02/22
 */
@Data
public class QiniuDTO {

    @NotEmpty(message = "Access Key不能为空")
    private String accesskey;

    @NotEmpty(message = "Secret Key不能为空")
    private String secretkey;

    @NotEmpty(message = "空间名不能为空")
    private String bucket;

    @NotEmpty(message = "绑定域名不能为空")
    private String host;

    @NotEmpty(message = "SSL选项不能为空")
    private Boolean usessl;

}
