package com.example.user.learnfunny.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.user.learnfunny.R;

public class MaskingActivity extends AppCompatActivity{
    //蒙版相关初始化
    private LinearLayout linearLayout_mask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masking_guide);

        linearLayout_mask = (LinearLayout)findViewById(R.id.linearLayout_mask);
        linearLayout_mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
