package com.example.user.learnfunny.module;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;


public class AboutModule extends BaseObservable {
    private String imgUrl;

    @Bindable
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @NonNull
    public String getString(String text) {
        String[] split = text.split(":");
        return split[1].trim() + ":" + split[2].trim();
    }
}
