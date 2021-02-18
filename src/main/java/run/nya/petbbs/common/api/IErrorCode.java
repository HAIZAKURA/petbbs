package run.nya.petbbs.common.api;

/**
 * API Code 接口
 */
public interface IErrorCode {

    /**
     * 错误编码  -1失败 200成功
     *
     * @return 错误编码
     */
    Integer getCode();

    /**
     * 错误描述
     *
     * @return 错误描述
     */
    String getMessage();

}
