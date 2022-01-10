package org.apache.apisix.plugin.runner.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther bnyte
 * @date 2021-10-22 21:40
 * @email bnytezz@163.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class R<T> implements Serializable {

    private Integer code;
    private String msg;
    private Long timestamp;
    private T data;

    public R<T> ok() {
        return this.initInfo(true);
    }

    public R<T> error() {
        return this.initInfo(false);
    }

    private R<T> initInfo(boolean isOk) {
        if (isOk) {
            this.code = RDesc.ok.getCode();
            this.msg = RDesc.ok.getMessage();
        } else {
            this.code = RDesc.error.getCode();
            this.msg = RDesc.error.getMessage();
        }
        this.timestamp = new Date().getTime();
        return this;
    }

    public R<T> msg(String msg) {
        this.code = this.code == null ? RDesc.ok.getCode() : this.code;
        this.msg = msg;
        return this;
    }

    public R<T> code(Integer code) {
        this.code = code;
        return this;
    }

    public R<T> codeAndMsg(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public R<T> data(T data) {
        this.data = data;
        return this;
    }

}
