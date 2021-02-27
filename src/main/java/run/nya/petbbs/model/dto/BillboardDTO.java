package run.nya.petbbs.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 公告DTO
 *
 * 2021/02/26
 */
@Data
public class BillboardDTO {

    @NotBlank(message = "公告内容不能为空")
    private String content;

}
