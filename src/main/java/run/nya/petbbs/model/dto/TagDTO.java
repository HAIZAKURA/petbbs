package run.nya.petbbs.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 标签DTO
 *
 * 2021/02/27
 */
@Data
public class TagDTO {

    @NotBlank(message = "标签名不能为空")
    private String name;

}
