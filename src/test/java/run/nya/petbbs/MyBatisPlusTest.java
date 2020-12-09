package run.nya.petbbs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import run.nya.petbbs.mapper.UserMapper;
import run.nya.petbbs.bean.SysUser;

@SpringBootTest
public class MyBatisPlusTest {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Test
    public void testSelectOne() {
        SysUser user = userMapper.selectById(1L);
        System.out.println(user);
    }
}
