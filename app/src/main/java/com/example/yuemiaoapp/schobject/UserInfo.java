package com.example.yuemiaoapp.schobject;

import org.litepal.crud.LitePalSupport;

public class UserInfo extends LitePalSupport {
    private Integer id;
    private String admin;
    private String password;
    private String imgUrl;//头像链接
    private String nickname;
    private String phone;//电话号码
    private String major;
    private Integer inoculate_id;//用户当前绑定的受种者信息
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getInoculate_id() {
        return inoculate_id;
    }

    public void setInoculate_id(Integer inoculate_id) {
        this.inoculate_id = inoculate_id;
    }

    public String getMajor() {
        return major;
    }
    public void setMajor(String major) {
        this.major = major;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
