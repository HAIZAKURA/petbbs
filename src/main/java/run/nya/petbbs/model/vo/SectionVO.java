package run.nya.petbbs.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import run.nya.petbbs.model.entity.SysSection;

/**
 * 专栏
 *
 * 2021/03/01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SectionVO extends SysSection {

    /**
     * 话题统计
     */
    private Integer posts;

    /**
     * 关注数
     */
//    private Integer followers;

}
