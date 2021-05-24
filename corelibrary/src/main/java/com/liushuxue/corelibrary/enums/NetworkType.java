package com.liushuxue.corelibrary.enums;

/**
 * @auther liushuxue
 * @describe    网络类型枚举
 * @createTime 2021/5/22
 */
public enum NetworkType {
    NETWORK_WIFI("WiFi"),
    NETWORK_5G("5G"),
    NETWORK_4G("4G"),
    NETWORK_2G("2G"),
    NETWORK_3G("3G"),
    NETWORK_UNKNOWN("Unknown"),
    NETWORK_NO("No network");

    private String desc;
    NetworkType(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return desc;
    }
}
