package run.nya.petbbs.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 评论DTO
 *
 * 2021/03/05
 */
@Data
public class CreateCommentDTO implements Serializable {

    private static final long serialVersionUID = -4827230560348009692L;

    private String postId;

    private String[] quoteIds;

    private String content;

}
