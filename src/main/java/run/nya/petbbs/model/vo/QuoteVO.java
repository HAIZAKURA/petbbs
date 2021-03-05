package run.nya.petbbs.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论-引用视图层
 *
 * 2021/03/05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteVO implements Serializable {

    private static final long serialVersionUID = 3259762405578784150L;

    private String id;

    private String userId;

    private String alias;

    private String username;

    private String content;

    private Date createTime;

}
