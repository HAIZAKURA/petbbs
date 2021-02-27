package run.nya.petbbs.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 友链DTO
 *
 * 2021/02/26
 */
@Data
public class FriendLinkDTO {

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "链接不能为空")
    private String link;

    @NotBlank(message = "图标不能为空")
    private String icon;

}
