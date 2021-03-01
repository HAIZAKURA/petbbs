package run.nya.petbbs.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 话题DTO
 *
 * 2021/03/01
 */
@Data
public class CreatePostDTO implements Serializable {

    private static final long serialVersionUID = 2847151357568196705L;

    private String title;

    private String content;

    private List<String> tags;

}
