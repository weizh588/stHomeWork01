package com.bluemsun.entity;

import java.util.Date;

/**
 * Created by zhi91 on 2020/4/24.
 */
public class Student implements Comparable<Student>{
    private Integer id ;//学号
    private String name;//姓名
    private String birDate;//出生日期
    private boolean gender;//性别

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirDate() {
        return birDate;
    }

    public void setBirDate(String birDate) {
        this.birDate = birDate;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    //让学号递增排序
    @Override
    public int compareTo(Student o) {
        return  this.id.compareTo(o.getId());
    }
}

