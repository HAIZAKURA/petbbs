package run.nya.petbbs;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import run.nya.petbbs.mapper.SysConfigMapper;
import run.nya.petbbs.model.entity.SysConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class MailTest {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Test
    public void sendMail() {
        MailAccount account = new MailAccount();
        MailUtil.send(account, "hhbilly99@126.com","账号激活", "123", false);
    }

}
