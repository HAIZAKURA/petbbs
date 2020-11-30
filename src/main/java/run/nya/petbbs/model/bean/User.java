package run.nya.petbbs.model.bean;

import lombok.Data;

@Data
public class User {
    private Long uid;
    private String uname;
    private String upass;
    private String umail;
}
