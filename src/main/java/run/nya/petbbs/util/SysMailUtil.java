package run.nya.petbbs.util;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import run.nya.petbbs.common.exception.ApiAsserts;
import run.nya.petbbs.mapper.SysConfigMapper;
import run.nya.petbbs.model.entity.SysConfig;

import javax.annotation.PostConstruct;

/**
 * 邮件工具类
 *
 * 2021/03/06
 */
@Component
public class SysMailUtil {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    private static SysMailUtil sysMailUtil;

    @PostConstruct
    public void init() {
        sysMailUtil = this;
        sysMailUtil.sysConfigMapper = this.sysConfigMapper;
    }

    /**
     * 发送邮件
     *
     * @param to
     * @param subject
     * @param content
     * @param isHtml
     */
    public Boolean sendMail(String to, String subject, String content, Boolean isHtml) {
        MailAccount account = new MailAccount();
        String mailFrom = sysMailUtil.sysConfigMapper
                .selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getItem, "mail_from"))
                .getValue();
        String mailUser = sysMailUtil.sysConfigMapper
                .selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getItem, "mail_user"))
                .getValue();
        String mailPass = sysMailUtil.sysConfigMapper
                .selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getItem, "mail_pass"))
                .getValue();
        String mailHost = sysMailUtil.sysConfigMapper
                .selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getItem, "mail_host"))
                .getValue();
        Integer mailPort = Integer
                .valueOf(sysMailUtil.sysConfigMapper
                    .selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getItem, "mail_port"))
                    .getValue());
        boolean mailSsl = sysMailUtil.sysConfigMapper
                .selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getItem, "mail_ssl"))
                .getValue()
                .equals("yes");
        account.setFrom(mailFrom);
        account.setUser(mailUser);
        account.setPass(mailPass);
        account.setHost(mailHost);
        account.setPort(mailPort);
        account.setSslEnable(mailSsl);
        try {
            MailUtil.send(account, to, subject, content, isHtml);
        } catch (Exception e) {
            ApiAsserts.fail("发送失败");
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
