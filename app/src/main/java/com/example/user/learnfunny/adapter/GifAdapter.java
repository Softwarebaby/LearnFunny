package com.example.user.learnfunny.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.blankj.utilcode.utils.LogUtils;
import com.bumptech.glide.Glide;
import com.example.user.learnfunny.R;
import com.example.user.learnfunny.bean.GIFBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

public class GifAdapter extends CommonAdapter<GIFBean.ResultBean> {
    private Context context;

    public GifAdapter(Context context, List<GIFBean.ResultBean> datas) {
        super(context, R.layout.item_gif, datas);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder holder, GIFBean.ResultBean gifBean, int position) {
        holder.setText(R.id.tv_gif_title, gifBean.getContent());
        String url = gifBean.getUrl();
        LogUtils.i("url?:" + url);
        if (url.endsWith("f")) {
            Glide.with(context)
                 .load(gifBean.getUrl())
                 .asGif()
                 .placeholder(R.mipmap.ic_error)
                 .into((ImageView) holder.getView(R.id.iv_gif));
        } else {
            Glide.with(context)
                 .load(gifBean.getUrl())
                 .placeholder(R.mipmap.ic_error)
                 .into((ImageView) holder.getView(R.id.iv_gif));
        }
    }
}
