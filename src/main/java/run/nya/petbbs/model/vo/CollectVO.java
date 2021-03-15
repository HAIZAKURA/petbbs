package run.nya.petbbs.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.nya.petbbs.model.entity.SysTag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 收藏视图层
 *
 * 2021/03/01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectVO implements Serializable {

    private static final long serialVersionUID = -7598030365118537414L;

    private String id;

    private String postId;

    private String userId;

    private String avatar;

    private String alias;

    private String username;

    private String title;

    private Integer comments;

    private Boolean top;

    private Boolean essence;

    private Integer collects;

    private List<SysTag> tags;

    private Integer view;

    private Date createTime;

    private Date modifyTime;

}
