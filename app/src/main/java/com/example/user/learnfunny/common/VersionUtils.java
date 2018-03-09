package com.example.user.learnfunny.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;

import com.blankj.utilcode.utils.AppUtils;
import com.blankj.utilcode.utils.LogUtils;
import com.blankj.utilcode.utils.NetworkUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.example.user.learnfunny.bean.VersionBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import okhttp3.Call;

/**
 *
 *@作者： Bob Du
 *@创建时间：2017/12/4 15:35
 *@文件名：VersionUtils.java
 *@功能：版本更新
 *
 */


public class VersionUtils {
    public static void updateVersion(final Context context) {
        //获取数据
        if(context != null){
        new AlertDialog.Builder(context).setTitle("发现新版本！")
                .setMessage("当前版本为:\t" + "1.0" + "\n" + "最新版本为:\t" + "1.2")
                .setNegativeButton("取消", null)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!NetworkUtils.isAvailableByPing()){
                            ToastUtils.showShortToast("当前网络不可用");
                            return;
                        }
                        //版本更新
                        VersionBean versionBean = new VersionBean();
                        versionUpdate(context, versionBean.getDownloadUrl());
                    }
                }).show();
        }else {

        }
    }
    private static void versionUpdate(final Context context, String url) {

        final ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setTitle("更新");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();
        ToastUtils.showShortToast("当前已经为最新版本");
        // 只呈现1s
        new Thread(new Runnable() {

            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                int progress = 0;

                while (System.currentTimeMillis() - startTime < 1000) {
                    try {
                        progress += 10;
                        dialog.setProgress(progress);
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        dialog.dismiss();
                    }
                }
                dialog.dismiss();
            }
        }).start();
        /*
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory()
                        .getAbsolutePath(), "QNews.apk") {
                    @Override
                    public void inProgress(float progress, long total, int id) {
                        dialog.setProgress((int) (progress * 100));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        LogUtils.i("e:" + e.getMessage());
                    }
                    @Override
                    public void onResponse(File response, int id) {
                        LogUtils.i("file:" + response.getAbsolutePath());
                        dialog.cancel();
                        AppUtils.installApp(context, response.getAbsolutePath());
                    }
                });
        */
    }
}
