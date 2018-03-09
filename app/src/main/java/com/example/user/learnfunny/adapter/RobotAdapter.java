package com.example.user.learnfunny.adapter;

import android.content.Context;

import com.example.user.learnfunny.bean.RobotMSGBean;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

public class RobotAdapter extends MultiItemTypeAdapter<RobotMSGBean> {
    private Context            context;
    private List<RobotMSGBean> datas;

    public RobotAdapter(Context context, List<RobotMSGBean> datas) {
        super(context, datas);
        this.context = context;
        this.datas = datas;
    }

    public void addDataToAdapter(RobotMSGBean bean) {
        if (datas != null) {
            datas.add(bean);
        }
    }
}
