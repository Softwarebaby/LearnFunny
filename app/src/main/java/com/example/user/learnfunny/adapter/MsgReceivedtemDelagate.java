package com.example.user.learnfunny.adapter;

import com.example.user.learnfunny.R;
import com.example.user.learnfunny.bean.RobotMSGBean;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

public class MsgReceivedtemDelagate implements ItemViewDelegate<RobotMSGBean> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_robot_receiver;
    }

    @Override
    public boolean isForViewType(RobotMSGBean item, int position) {
        return item.getType() == RobotMSGBean.MSG_RECEIVED;
    }

    @Override
    public void convert(ViewHolder holder, RobotMSGBean robotMSGBean, int position) {
        holder.setText(R.id.tv_msg_left, robotMSGBean.getMsg());
    }
}
