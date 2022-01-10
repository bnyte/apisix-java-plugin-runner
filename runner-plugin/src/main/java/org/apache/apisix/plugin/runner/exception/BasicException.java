package org.apache.apisix.plugin.runner.exception;

import org.apache.apisix.plugin.runner.response.RDesc;

/**
 * @auther bnyte
 * @date 2021-12-26 05:00
 * @email bnytezz@gmail.com
 */
public class BasicException extends RuntimeException {

    private String message;
    private Integer code;
    private RDesc desc;

    public BasicException(RDesc desc) {
        super(desc.getMessage());
        this.message = desc.getMessage();
        this.code = desc.getCode();
        this.desc = desc;
    }

    public BasicException(Integer code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public BasicException(String message) {
        super(message);
        this.message = message;
    }

    public BasicException(String message, Throwable cause) {
        super(message, cause);
    }

    public BasicException(Throwable cause) {
        super(cause);
    }

    public BasicException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public RDesc getDesc() {
        return desc;
    }

    public void setDesc(RDesc desc) {
        this.desc = desc;
    }
}
