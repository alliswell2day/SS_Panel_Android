package com.example.huangzumou.myapplication.model.bean;

/**
 * Created by huangzumou on 2017/4/27.
 */

public class SpeedBean {

    /**
     *
     "id":97,
     "nodeid":3,
     "datetime":1493274746,
     "telecomping":"3.072 ms",
     "telecomeupload":"174.8 Mbit\/s",
     "telecomedownload":"760.85 Mbit\/s",
     "unicomping":"14.733 ms",
     "unicomupload":"5.94 Mbit\/s",
     "unicomdownload":"97.73 Mbit\/s",
     "cmccping":"14.733 ms",
     "cmccupload":"114.76 Mbit\/s",
     "cmccdownload":"2.48 Mbit\/s"}
     *
     *
     */

    private int id;
    private int nodeid;
    private long datetime;
    private String telecomping;
    private String telecomeupload;
    private String telecomedownload;
    private String unicomping;
    private String unicomupload;
    private String unicomdownload;
    private String cmccping;
    private String cmccupload;
    private String cmccdownload;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNodeid() {
        return nodeid;
    }

    public void setNodeid(int nodeid) {
        this.nodeid = nodeid;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public String getTelecomping() {
        return telecomping;
    }

    public void setTelecomping(String telecomping) {
        this.telecomping = telecomping;
    }

    public String getTelecomeupload() {
        return telecomeupload;
    }

    public void setTelecomeupload(String telecomeupload) {
        this.telecomeupload = telecomeupload;
    }

    public String getTelecomedownload() {
        return telecomedownload;
    }

    public void setTelecomedownload(String telecomedownload) {
        this.telecomedownload = telecomedownload;
    }

    public String getUnicomping() {
        return unicomping;
    }

    public void setUnicomping(String unicomping) {
        this.unicomping = unicomping;
    }

    public String getUnicomupload() {
        return unicomupload;
    }

    public void setUnicomupload(String unicomupload) {
        this.unicomupload = unicomupload;
    }

    public String getUnicomdownload() {
        return unicomdownload;
    }

    public void setUnicomdownload(String unicomdownload) {
        this.unicomdownload = unicomdownload;
    }

    public String getCmccping() {
        return cmccping;
    }

    public void setCmccping(String cmccping) {
        this.cmccping = cmccping;
    }

    public String getCmccupload() {
        return cmccupload;
    }

    public void setCmccupload(String cmccupload) {
        this.cmccupload = cmccupload;
    }

    public String getCmccdownload() {
        return cmccdownload;
    }

    public void setCmccdownload(String cmccdownload) {
        this.cmccdownload = cmccdownload;
    }
}
