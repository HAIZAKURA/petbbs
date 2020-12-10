package run.nya.petbbs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SysUser {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String userName;
    private String userPass;
    private String userMail;
    private String userAvat;
    private Integer userAge;
    private Integer userSex;
    private String userDesc;
    private Integer userStat;
}
