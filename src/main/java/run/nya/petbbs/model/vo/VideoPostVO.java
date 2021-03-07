package run.nya.petbbs.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import run.nya.petbbs.model.entity.SysTag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 视频话题视图层
 *
 * 2021/03/01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoPostVO implements Serializable {

    private static final long serialVersionUID = 8748541635040036712L;

    private String id;

    private String userId;

    private String avatar;

    private String alias;

    private String username;

    private String title;

    private String cover;

    private String content;

    private Integer comments;

    private Integer collects;

    private Integer view;

    private List<SysTag> tags;

    private Date createTime;

    private Date modifyTime;

}
