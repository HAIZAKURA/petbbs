package run.nya.petbbs.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 专栏DTO
 *
 * 2021/02/27
 */
@Data
public class SectionDTO {

    @NotBlank(message = "专栏作者不能为空")
    private String userId;

    @NotBlank(message = "专栏标题不能为空")
    private String title;

    @NotBlank(message = "专栏描述不能为空")
    private String description;

    @NotBlank(message = "专栏图片不能为空")
    private String thumbnail;

}
