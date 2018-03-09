package com.example.user.learnfunny.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.user.learnfunny.R;
import com.example.user.learnfunny.bean.JokeBean;


public class JokeAdapter extends BaseQuickAdapter<JokeBean.ResultBean.DataBean, BaseViewHolder> {

    public JokeAdapter() {
        super(R.layout.item_joke);
    }

    @Override
    protected void convert(BaseViewHolder helper, JokeBean.ResultBean.DataBean item) {
        helper.setText(R.id.tv_joke_content, item.getContent());
        helper.setText(R.id.tv_joke_date, item.getUpdatetime());
    }

}
