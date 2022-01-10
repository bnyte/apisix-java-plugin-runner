package org.apache.apisix.plugin.runner.enums;

import org.springframework.util.StringUtils;

/**
 * @auther bnyte
 * @date 2022-01-06 14:22
 * @email bnytezz@gmail.com
 */
public enum APIVersion {

    v1("v1", "release version 1"),
    ;

    private String version;
    private String remark;

    APIVersion(String version, String remark) {
        this.version = version;
        this.remark = remark;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "APIVersion{" +
                "version='" + version + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }

    public static APIVersion version(String version) {
        if (!StringUtils.hasText(version)) return null;
        for (APIVersion value : values()) {
            if (value.version.equals(version)) return value;
        }
        return null;
    }
}
