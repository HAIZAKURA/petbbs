package run.nya.petbbs.model.dto;

import lombok.Data;
import run.nya.petbbs.model.entity.SysTag;

import java.io.Serializable;
import java.util.List;

/**
 * 视频DTO
 *
 * 2021/03/07
 */
@Data
public class CreateVideoDTO implements Serializable {

    private static final long serialVersionUID = 4027746303799780739L;

    private String title;

    private String video;

    private String content;

    private List<SysTag> tags;

}
