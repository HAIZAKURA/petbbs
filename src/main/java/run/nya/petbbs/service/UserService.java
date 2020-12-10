package run.nya.petbbs.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.nya.petbbs.entity.SysUser;
import run.nya.petbbs.mapper.UserMapper;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired(required = false)
    private UserMapper userMapper;

    public List<SysUser> getAllUser() {
        return userMapper.selectList(new QueryWrapper<>());
    }
}
