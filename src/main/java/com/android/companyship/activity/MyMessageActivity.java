package com.android.companyship.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.companyship.R;
import com.android.companyship.bean.Examinebean;
import com.android.companyship.view.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2016/9/12.
 */
public class MyMessageActivity extends Activity {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_titlename)
    TextView tvTitlename;
    @BindView(R.id.rlMainTitle)
    RelativeLayout rlMainTitle;
    @BindView(R.id.lv_newState)
    ListViewForScrollView lvNewState;


    private List<Examinebean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mymessage);
        ButterKnife.bind(this);

//        addData();
        initData();
    }

//    //添加本地数据
//    private void addData() {
//        Examinebean examinebean;
//
//        for (int i = 0; i < 10; i++) {
//            examinebean = new Examinebean();
//            examinebean.setInspectionunmber("34567265743");
//            examinebean.setEnglishnameship("CH-HN_ThePacificOcean");
//            examinebean.setVoyagenumber("fhrufrf");
//            examinebean.setDeliveryNumbers("20160908115820");
//            list.add(examinebean);
//        }
//
//    }

    //初始化数据
    private void initData() {
        tvTitlename.setText("我的消息");
//        ExamineAdapter adapter = new ExamineAdapter(this,list);
//        lvNewState.setAdapter(adapter);
    }

    @OnClick({R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()) {
            //返回上一个页面
            case R.id.ll_back:
                this.finish();
                break;
        }
    }
}
