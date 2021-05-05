package com.example.justdraw.Utilities;

public  class PostModel {
    private String mName;
    private String mImgUrl;

    public PostModel(String name, String imgUrl){
        this.mName = name;
        this.mImgUrl= imgUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName=name;
    }

    public String getImgUrl() {
        return mImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        mImgUrl=imgUrl;
    }
}
