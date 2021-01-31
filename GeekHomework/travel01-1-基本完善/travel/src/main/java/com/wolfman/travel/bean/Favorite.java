package com.wolfman.travel.bean;

import java.io.Serializable;

/**
 * 收藏实体类
 */
public class Favorite implements Serializable {
    private String rid;//旅游线路对象
    private String date;//收藏时间
    private String uid;//所属用户

    public Favorite() {
    }

    public Favorite(String rid, String date, String uid) {
        this.rid = rid;
        this.date = date;
        this.uid = uid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRid() {
        return rid;
    }

    public String getDate() {
        return date;
    }

    public String getUid() {
        return uid;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "rid='" + rid + '\'' +
                ", date='" + date + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
