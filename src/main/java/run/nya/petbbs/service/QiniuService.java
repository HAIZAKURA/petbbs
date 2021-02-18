package run.nya.petbbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.nya.petbbs.model.entity.QiniuConfig;
import run.nya.petbbs.mapper.QiniuMapper;

@Service
@Transactional
public class QiniuService {

    @Autowired(required = false)
    private QiniuMapper qiniuMapper;

    public QiniuConfig getQiniuConfig() {
        QiniuConfig qiniuConfig = qiniuMapper.selectById(1L);
        return qiniuConfig;
    }
}
