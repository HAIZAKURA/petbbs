package run.nya.petbbs.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 照片视图层
 *
 * 2021/03/14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoVO implements Serializable {

    private static final long serialVersionUID = -6869276585446902735L;

    private String id;

    private String userId;

    private String avatar;

    private String alias;

    private String username;

    private String photo;

    private String content;

    private Integer comments;

    private Integer view;

    private Date createTime;

}
