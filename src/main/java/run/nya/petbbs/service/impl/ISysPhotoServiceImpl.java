package run.nya.petbbs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import run.nya.petbbs.common.exception.ApiAsserts;
import run.nya.petbbs.mapper.SysPhotoMapper;
import run.nya.petbbs.mapper.SysUserMapper;
import run.nya.petbbs.model.dto.CreatePhotoDTO;
import run.nya.petbbs.model.entity.SysPhoto;
import run.nya.petbbs.model.entity.SysUser;
import run.nya.petbbs.model.vo.PhotoVO;
import run.nya.petbbs.model.vo.ProfileVO;
import run.nya.petbbs.service.ISysPhotoService;
import run.nya.petbbs.service.ISysUserService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 照片接口实现类
 *
 * 2021/03/14
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ISysPhotoServiceImpl extends ServiceImpl<SysPhotoMapper, SysPhoto> implements ISysPhotoService {

    @Autowired
    private ISysUserService iSysUserService;

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public Page<PhotoVO> getList(Page<PhotoVO> page) {
        Page<PhotoVO> iPage = baseMapper.getList(page);
        return iPage;
    }

    @Override
    public Map<String, Object> viewPhoto(String id) {
        Map<String, Object> map = new HashMap<>(16);
        SysPhoto sysPhoto = baseMapper.selectById(id);
        if (ObjectUtils.isEmpty(sysPhoto)) {
            ApiAsserts.fail("照片不存在");
        }
        sysPhoto.setView(sysPhoto.getView() + 1);
        baseMapper.updateById(sysPhoto);
        sysPhoto.setContent(EmojiParser.parseToUnicode(sysPhoto.getContent()));
        map.put("photo", sysPhoto);
        ProfileVO user = iSysUserService.getUserProfile(sysPhoto.getUserId());
        map.put("user", user);
        return map;
    }

    @Override
    public SysPhoto create(CreatePhotoDTO dto, SysUser user) {
        SysPhoto photoIsExist = baseMapper.selectOne(new LambdaQueryWrapper<SysPhoto>().eq(SysPhoto::getPhoto, dto.getPhoto()));
        if (!ObjectUtils.isEmpty(photoIsExist)) {
            ApiAsserts.fail("照片已存在");
        }
        SysPhoto photo = SysPhoto.builder()
                .photo(dto.getPhoto())
                .content(EmojiParser.parseToUnicode(dto.getContent()))
                .userId(user.getId())
                .createTime(new Date())
                .build();
        baseMapper.insert(photo);
        Integer newScore = user.getScore() + 1;
        sysUserMapper.updateById(user.setScore(newScore));
        return photo;
    }

}
