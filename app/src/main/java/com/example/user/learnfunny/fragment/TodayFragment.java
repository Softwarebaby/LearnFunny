package com.example.user.learnfunny.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.user.learnfunny.R;
import com.example.user.learnfunny.adapter.TodayAdapter;
import com.example.user.learnfunny.bean.TodayOfHistoryBean;
import com.example.user.learnfunny.common.Constants;
import com.example.user.learnfunny.databinding.FragmentTodayBinding;
import com.example.user.learnfunny.net.QClitent;
import com.example.user.learnfunny.net.QNewsService;
import com.example.user.learnfunny.activity.TodayDetailActivity;
import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TodayFragment extends Fragment {


    //历史上今天的适配器
    private TodayAdapter adapter;
    private FragmentTodayBinding mTodayBinding;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mTodayBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_today, null, false);
        View view = mTodayBinding.getRoot();

        adapter = new TodayAdapter();
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);

        //悬浮按钮设置点击事件
        mTodayBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTodayBinding.rvToday.smoothScrollToPosition(0);
            }
        });

        //recyclerView初始化
        mTodayBinding.rvToday.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mTodayBinding.rvToday.setAdapter(adapter);
        mTodayBinding.rvToday.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), TodayDetailActivity.class);
                intent.putExtra("e_id", ((TodayOfHistoryBean.ResultBean) adapter.getItem(position)).getE_id());
                getActivity().startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });

        //获得当前的日期
        Calendar  calendar = Calendar.getInstance();
        final int month    = calendar.get(Calendar.MONTH) + 1;
        final int day      = calendar.get(Calendar.DAY_OF_MONTH);
        //        tbToday.setTitle("历史上的今天 (" + month + "月" + day + "日)");

        String params = month + "/" + day;
        //初次加载数据
        QClitent.getInstance()
                .create(QNewsService.class, Constants.BASE_JUHE_URL)
                .getTodayOfHistoryData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TodayOfHistoryBean>() {
                    @Override
                    public void accept(TodayOfHistoryBean todayOfHistoryBean) throws Exception {
                        List<TodayOfHistoryBean.ResultBean> result = todayOfHistoryBean.getResult();
                        adapter.addData(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
                    }
                });

        return view;
    }
}
