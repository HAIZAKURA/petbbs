package run.nya.petbbs;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import run.nya.petbbs.entity.SmmsConfig;
import run.nya.petbbs.mapper.SmmsMapper;
import run.nya.petbbs.mapper.UserMapper;
import run.nya.petbbs.entity.SysUser;

@SpringBootTest
public class MyBatisPlusTest {

    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private SmmsMapper smmsMapper;

    @Test
    public void testSelectOne() {
        SysUser user = userMapper.selectById(1L);
        System.out.println(user);
    }

    @Test
    public void testSelectSmmsToken() {
        QueryWrapper<SmmsConfig> query = new QueryWrapper<>();
        query.eq("smms_item", "token");
//        SmmsConfig smmsConfig = smmsMapper.selectById(1L);
        SmmsConfig smms = smmsMapper.selectOne(query);
        System.out.println(smms.getSmmsValue());
    }

    @Test
    public void testUpdateSmmsToken() {
        UpdateWrapper<SmmsConfig> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("smms_item", "token");
        updateWrapper.set("smms_value", null);
        System.out.println(smmsMapper.update(new SmmsConfig(), updateWrapper));
    }
}
