package run.nya.petbbs.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 七牛DTO
 *
 * 2021/02/22
 */
@Data
public class QiniuDTO {

    @NotBlank(message = "Access Key不能为空")
    private String accesskey;

    @NotBlank(message = "Secret Key不能为空")
    private String secretkey;

    @NotBlank(message = "空间名不能为空")
    private String bucket;

    @NotBlank(message = "绑定域名不能为空")
    private String host;

    @NotBlank(message = "SSL选项不能为空")
    private Boolean usessl;

}
