package com.example.user.learnfunny.bean;

public class VersionBean{

    @Override
    public String toString() {
        return "VersionBean{" +
                "versionName='" + versionName + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
    private String versionName;
    private String downloadUrl;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
