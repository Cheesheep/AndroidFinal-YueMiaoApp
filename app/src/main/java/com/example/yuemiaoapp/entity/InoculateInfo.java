package com.example.yuemiaoapp.entity;

import org.litepal.crud.LitePalSupport;

public class InoculateInfo extends LitePalSupport {
    private Integer id;
    private String name;
    private String id_type;
    private String identity;
    private String birth;
    private String sex;
    private String phone;
    private String location;
    private String loc_detail;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    private Integer user_id; //与用户关联

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLoc_detail() {
        return loc_detail;
    }

    public void setLoc_detail(String loc_detail) {
        this.loc_detail = loc_detail;
    }
}
