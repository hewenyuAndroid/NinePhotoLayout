package com.hwy.ninephotolayout.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: hewenyu
 * 日期: 2018/10/25 11:10
 * 说明:
 */
public class NoticeBean implements Serializable {

    private String name;

    private String content;

    private List<String> mPhotos;

    public NoticeBean(String name, String content){
        this.name = name;
        this.content = content;
        mPhotos = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPhotos(List<String> mPhotos) {
        this.mPhotos = mPhotos;
    }

    public List<String> getPhotos() {
        return mPhotos;
    }
}
