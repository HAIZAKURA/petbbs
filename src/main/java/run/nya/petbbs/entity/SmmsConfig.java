package run.nya.petbbs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SmmsConfig {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String smmsItem;
    private String smmsValue;
}
