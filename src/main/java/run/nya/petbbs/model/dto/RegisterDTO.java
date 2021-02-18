package run.nya.petbbs.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * 用户注册DTO
 */
@Data
public class RegisterDTO {

    @NotEmpty(message = "请输入用户名")
    @Length(min = 3, max = 15, message = "用户名长度应为2-15")
    private String name;

    @NotEmpty(message = "请输入密码")
    @Length(min = 6, max = 20, message = "密码长度应为2-15")
    private String pass;

    @NotEmpty(message = "请再次输入密码")
    @Length(min = 6, max = 20, message = "密码长度应为2-15")
    private String checkPass;

    @NotEmpty(message = "请输入邮箱")
    @Email(message = "邮箱格式错误")
    private String email;

}
