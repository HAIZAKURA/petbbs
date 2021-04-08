package run.nya.petbbs.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * 通知DTO
 *
 * 2021/04/08
 */
@Data
public class NotifyDTO {

    private String userId;

    private String content;

    private String remark;

}
