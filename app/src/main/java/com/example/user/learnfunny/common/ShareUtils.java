package com.example.user.learnfunny.common;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 *
 *@作者： Bob Du
 *@创建时间：2017/12/2 10:55
 *@文件名：ShareUtils.java
 *@功能：分享功能
 *
 */

public class ShareUtils {

    public static void share(Context context, String shareContent) {
        StringBuffer sb = new StringBuffer();
        sb.append(shareContent);
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, sb.toString());
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "该手机不支持该操作", Toast.LENGTH_LONG).show();
        }
    }

}