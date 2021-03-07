package run.nya.petbbs.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import run.nya.petbbs.common.exception.ApiAsserts;
import run.nya.petbbs.config.redis.RedisService;
import run.nya.petbbs.config.security.util.JwtTokenUtil;
import run.nya.petbbs.mapper.SysConfigMapper;
import run.nya.petbbs.mapper.SysPostMapper;
import run.nya.petbbs.mapper.SysSectionMapper;
import run.nya.petbbs.mapper.SysUserMapper;
import run.nya.petbbs.model.dto.LoginDTO;
import run.nya.petbbs.model.dto.RegisterDTO;
import run.nya.petbbs.model.entity.SysConfig;
import run.nya.petbbs.model.entity.SysPost;
import run.nya.petbbs.model.entity.SysSection;
import run.nya.petbbs.model.entity.SysUser;
import run.nya.petbbs.model.vo.ProfileVO;
import run.nya.petbbs.service.ISysUserService;
import run.nya.petbbs.util.SysMailUtil;

import java.util.Date;

/**
 * 用户实现类
 *
 * 2021/02/19
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ISysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysPostMapper sysPostMapper;

    @Autowired
    private SysSectionMapper sysSectionMapper;

    @Autowired
    private SysConfigMapper sysConfigMapper;

//    @Value("${web.domain}")
//    private String domain;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisService redisService;

    /**
     * 通过用户名获取用户
     *
     * @param  username
     * @return
     */
    @Override
    public SysUser getUserByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        return baseMapper.selectOne(wrapper);
    }

    /**
     * 用户登录
     *
     * @param  dto
     * @return
     */
    @Override
    public String executeLogin(LoginDTO dto) {
        String token = null;
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(dto.getUsername());
            if (!userDetails.isEnabled()) {
                ApiAsserts.fail("账号已被封禁");
            }
            boolean matches = passwordEncoder.matches(dto.getPassword(), userDetails.getPassword());
            if (!matches) {
                throw new BadCredentialsException("密码错误");
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (UsernameNotFoundException e) {
            log.warn("用户不存在：", dto.getUsername());
        } catch (BadCredentialsException e) {
            log.warn("密码错误：", dto.getPassword());
        }
        return token;
    }

    /**
     * 用户注册
     *
     * @param  dto
     * @return
     */
    @Override
    public SysUser executeRegister(RegisterDTO dto) {
        //查询用户名是否存在
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, dto.getName()).or().eq(SysUser::getEmail, dto.getEmail());
        SysUser sysUser = baseMapper.selectOne(wrapper);
        if (!ObjectUtils.isEmpty(sysUser)) {
            ApiAsserts.fail("账号或邮箱已存在！");
        }
        SysUser addUser = SysUser.builder()
                .username(dto.getName())
                .alias(dto.getName())
                .password(passwordEncoder.encode(dto.getPass()))
                .email(dto.getEmail())
                .createTime(new Date())
                .status(true)
                .build();
        baseMapper.insert(addUser);

        // 账号激活
        String activeCode = RandomUtil.randomString(8);
        redisService.set("activeCode[" + dto.getName() + "]", activeCode, 30 * 60);
        // 发送邮件
        String domain = sysConfigMapper
                .selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getItem, "domain"))
                .getValue();
        String activeUrl = URLUtil.normalize(domain + "/#?user=" + dto.getName() + "&code=" + activeCode);
        String content = "请在30分钟内激活您的账号，如非本人操作，请忽略 </br > " +
                "<a href=\"" + activeUrl + "\" target =\"_blank\" '>点击激活账号</a>";
        ThreadUtil.execAsync(() -> {
            try {
                SysMailUtil sysMailUtil = new SysMailUtil();
                sysMailUtil.sendMail(dto.getEmail(), "账号激活", content, true);
//                MailUtil.send(dto.getEmail(), "账号激活", content, true);
            } catch (Exception e) {
                ApiAsserts.fail("发送失败！");
            }
        });
        return addUser;
    }

    /**
     * 获取用户信息
     *
     * @param  id 用户ID
     * @return ProfileVO
     */
    @Override
    public ProfileVO getUserProfile(String id) {
        ProfileVO profileVO = new ProfileVO();
        SysUser sysUser = baseMapper.selectById(id);
        BeanUtils.copyProperties(sysUser, profileVO);
        // 用户话题数
        Integer postCount = sysPostMapper.selectCount(new LambdaQueryWrapper<SysPost>().eq(SysPost::getUserId, id));
        profileVO.setPostCount(postCount);
        // 专栏数
        Integer sectionCount = sysSectionMapper.selectCount(new LambdaQueryWrapper<SysSection>().eq(SysSection::getUserId, id));
        profileVO.setSections(sectionCount);
        return profileVO;
    }

    /**
     * 重新发送激活码
     *
     * @param name
     * @param email
     */
    @Override
    public void reActive(String name, String email) {
        redisService.del("activeCode[" + name + "]");
        String activeCode = RandomUtil.randomString(8);
        redisService.set("activeCode[" + name + "]", activeCode, 30 * 60);
        String domain = sysConfigMapper
                .selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getItem, "domain"))
                .getValue();
        String activeUrl = URLUtil.normalize(domain + "/#?user=" + name + "&code=" + activeCode);
        String content = "请在30分钟内激活您的账号，如非本人操作，请忽略 </br > " +
                "<a href=\"" + activeUrl + "\" target =\"_blank\" '>点击激活账号</a>";
        ThreadUtil.execAsync(() -> {
            try {
                SysMailUtil sysMailUtil = new SysMailUtil();
                sysMailUtil.sendMail(email, "账号激活", content, true);
//                MailUtil.send(dto.getEmail(), "账号激活", content, true);
            } catch (Exception e) {
                ApiAsserts.fail("发送失败！");
            }
        });
    }

}
