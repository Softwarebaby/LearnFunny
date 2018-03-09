package com.example.user.learnfunny.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.utils.LogUtils;
import com.example.user.learnfunny.R;
import com.example.user.learnfunny.databinding.FragmentAboutBinding;
import com.example.user.learnfunny.module.AboutModule;
import com.xiawei.webviewlib.WebViewActivity;


public class AboutFragment extends Fragment {

    private AboutModule mAboutModule;
    private FragmentAboutBinding mAboutBinding;
    private TextView tvJianshu,tvBlog;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mAboutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, null, false);
        View view = mAboutBinding.getRoot();

        mAboutModule = new AboutModule();
        mAboutModule.setImgUrl(getString(R.string.about_background));
        //mAboutBinding.setAbout(mAboutModule);
        //mAboutBinding.setPresenter(new Presenter());
        return view;
    }
}
