package run.nya.petbbs.util;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import run.nya.petbbs.mapper.SysConfigMapper;
import run.nya.petbbs.model.entity.SysConfig;

import javax.annotation.Resource;

/**
 * 邮件工具类
 *
 * 2021/03/06
 */
public class SysMailUtil {

    @Resource
    private SysConfigMapper sysConfigMapper;

    public void sendMail(String to, String subject, String content, Boolean isHtml) {
        MailAccount account = new MailAccount();
        String mailFrom = sysConfigMapper
                .selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getItem, "mail_from"))
                .getValue();
        String mailUser = sysConfigMapper
                .selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getItem, "mail_user"))
                .getValue();
        String mailPass = sysConfigMapper
                .selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getItem, "mail_pass"))
                .getValue();
        String mailHost = sysConfigMapper
                .selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getItem, "mail_host"))
                .getValue();
        Integer mailPort = Integer
                .valueOf(sysConfigMapper
                    .selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getItem, "mail_port"))
                    .getValue());
        Boolean mailSsl = sysConfigMapper
                .selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getItem, "mail_ssl"))
                .getValue()
                .equals("yes") ? Boolean.TRUE : Boolean.FALSE;
        account.setFrom(mailFrom);
        account.setUser(mailUser);
        account.setPass(mailPass);
        account.setHost(mailHost);
        account.setPort(mailPort);
        account.setSslEnable(mailSsl);
        MailUtil.send(account, to, subject, content, isHtml);
    }

}
