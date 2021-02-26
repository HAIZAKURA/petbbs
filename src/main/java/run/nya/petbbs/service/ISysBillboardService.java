package run.nya.petbbs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import run.nya.petbbs.model.dto.BillboardDTO;
import run.nya.petbbs.model.entity.SysBillboard;

/**
 * 公告接口
 *
 * 2021/02/26
 */
public interface ISysBillboardService extends IService<SysBillboard> {

    /**
     * 添加公告
     *
     * @param  dto
     * @return SysBillboard
     */
    SysBillboard addBillboard(BillboardDTO dto);

}
