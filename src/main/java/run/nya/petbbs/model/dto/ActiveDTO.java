package run.nya.petbbs.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 账号激活DTO
 *
 * 2021/02/20
 */
@Data
public class ActiveDTO {

    @NotBlank(message = "账号不能为空")
    private String user;

    @NotBlank(message = "激活码不能为空")
    private String code;

}
