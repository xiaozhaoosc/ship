package com.android.companyship.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.companyship.R;
import com.android.companyship.util.Util;
import com.android.companyship.view.ListViewForScrollView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2016/9/12.
 */
public class MyStatisticsActivity extends Activity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_titlename)
    TextView tvTitlename;
    @BindView(R.id.tv_notdata)
    TextView tvNotdata;
    @BindView(R.id.lv_classify)
    ListViewForScrollView lvClassify;
    @BindView(R.id.tv_startdate)
    TextView tvStartdate;
    @BindView(R.id.tv_enddate)
    TextView tvEnddate;
    @BindView(R.id.btn_query)
    Button btnQuery;

    private int mYear; //定义时间年
    private String date; //显示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mystatistics);
        ButterKnife.bind(this);

        initData();
    }

    //初始化数据
    private void initData() {
        tvTitlename.setText("我的统计");
        Calendar calendar1 = Calendar.getInstance();
//        String time = calendar1.get(Calendar.YEAR) + "-" + (calendar1.get(Calendar.MONTH) + 1) + "-" +
//                calendar1.get(Calendar.DAY_OF_MONTH);

        mYear = calendar1.get(Calendar.YEAR);
        int mMouth = calendar1.get(Calendar.MONTH);
        int mDate = calendar1.get(Calendar.DAY_OF_MONTH);
        changeDate(mMouth, mDate);
        tvStartdate.setText(date);
        tvEnddate.setText(date);
    }


    @OnClick({R.id.ll_back, R.id.tv_startdate, R.id.tv_enddate, R.id.btn_query})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            //返回上一页面
            case R.id.ll_back:
                this.finish();
                break;
            //开始时间
            case R.id.tv_startdate:
                Util.showData(MyStatisticsActivity.this,tvStartdate);
                break;
            //结束时间
            case R.id.tv_enddate:
                Util.showData(MyStatisticsActivity.this,tvEnddate);
                break;
            //点击查询
            case R.id.btn_query:
                Toast.makeText(this,"点击查询",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 设置时间显示的正确的格式
     *
     * @param mMouth
     * @param mDate
     */
    public void changeDate(int mMouth, int mDate) {
        String str_mouth;
        String str_date;

        if (mMouth < 10) {
            str_mouth = "0" + (mMouth + 1);
        } else {
            str_mouth = (mMouth + 1) + "";
        }
        if (mDate < 10) {
            str_date = "0" + mDate;
        } else {
            str_date = mDate + "";
        }
        date = mYear + "-" + str_mouth + "-" + str_date;
    }

}
