package run.nya.petbbs.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BillboardDTO {

    @NotBlank(message = "公告内容不能为空")
    private String content;

}
