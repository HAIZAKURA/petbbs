package run.nya.petbbs.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 照片DTO
 *
 * 2012/03/14
 */
@Data
public class CreatePhotoDTO implements Serializable {

    private static final long serialVersionUID = -4037964229412195434L;

    private String photo;

    private String content;

//    private List<SysTag> tags;

}
