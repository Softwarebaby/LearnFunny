package com.example.user.learnfunny.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.user.learnfunny.R;
import com.example.user.learnfunny.bean.TodayOfHistoryBean;


public class TodayAdapter extends BaseQuickAdapter<TodayOfHistoryBean.ResultBean, BaseViewHolder> {


    public TodayAdapter() {
        super(R.layout.item_today);
    }

    @Override
    protected void convert(BaseViewHolder helper, TodayOfHistoryBean.ResultBean item) {
        helper.setText(R.id.tv_today_title, item.getTitle());
        helper.setText(R.id.tv_today_date, item.getDate());
        helper.addOnClickListener(R.id.ll_today_detail);
    }
}
