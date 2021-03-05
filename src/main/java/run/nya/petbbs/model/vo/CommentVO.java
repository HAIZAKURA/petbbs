package run.nya.petbbs.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评论视图层
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVO implements Serializable {

    private static final long serialVersionUID = -3249821828688199309L;

    private String id;

    private String userId;

    private String alias;

    private String avatar;

    private String username;

    private List<QuoteVO> quotes;

    private String content;

    private Date createTime;

}
