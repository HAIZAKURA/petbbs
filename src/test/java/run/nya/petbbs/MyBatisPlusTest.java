package run.nya.petbbs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import run.nya.petbbs.mapper.SysUserMapper;
import run.nya.petbbs.model.entity.SysUser;

@SpringBootTest
public class MyBatisPlusTest {

    @Autowired(required = false)
    private SysUserMapper sysUserMapper;

    @Test
    public void testSelectOne() {
        SysUser user = sysUserMapper.selectById(1L);
        System.out.println(user);
    }

}
