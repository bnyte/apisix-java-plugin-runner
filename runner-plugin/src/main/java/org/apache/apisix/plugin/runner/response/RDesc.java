package org.apache.apisix.plugin.runner.response;

import java.io.Serializable;

/**
 * @auther bnyte
 * @date 2021-12-09 18:42
 * @email bnytezz@gmail.com
 */
public enum RDesc implements Serializable {
    ok(0, "succeeded"),
    error(-1, "failed"),

    tokenAuthError(403, "auth error"),
    ;
    private static final long serialVersionUID = 94264211209183701L;

    /**
     * http response code
     */
    private Integer code;
    /**
     * http response message
     */
    private String message;

    RDesc(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
