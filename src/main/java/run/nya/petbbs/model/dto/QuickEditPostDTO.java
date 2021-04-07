package run.nya.petbbs.model.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 话题快速修改DTO
 *
 * 2021/04/06
 */
@Data
public class QuickEditPostDTO {

    @NotEmpty(message = "不能为空")
    private String id;

    private Boolean top;

    private Boolean essence;

}
