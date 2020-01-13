package com.android.companyship.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.companyship.R;
import com.android.companyship.util.SharedPreUtil;
import com.android.companyship.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by lenovo on 2016/9/7.
 *   主页面
 */
public class MainActivity extends Activity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_titlename)
    TextView tvTitlename;
    @BindView(R.id.ln_make_appointment)
    LinearLayout lnMakeAppointment;
    @BindView(R.id.ln_myorder)
    LinearLayout lnMyorder;
    @BindView(R.id.ln_wantcontact)
    LinearLayout lnWantcontact;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.ln_mymessage)
    LinearLayout lnMymessage;
    @BindView(R.id.ln_mystatistics)
    LinearLayout lnMystatistics;
    @BindView(R.id.ln_exit)
    LinearLayout lnExit;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_versionName)
    TextView tvVersionName;
    @BindView(R.id.iv_phone)
    ImageView ivPhone;
    @BindView(R.id.tv_phoneNum)
    TextView tvPhoneNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initDate();
    }

    //初始化数据
    private void initDate() {
        tvTitlename.setText("上海出入境检验检疫局危险品查验预约系统");
        if (SharedPreUtil.getTypritem(MainActivity.this).equals("1")){
            tvName.setText("当前用户：" + SharedPreUtil.getCompanynamech(MainActivity.this));
        } else if (SharedPreUtil.getTypritem(MainActivity.this).equals("2")){
            tvName.setText("当前用户：" + SharedPreUtil.getCUname(MainActivity.this));
        }

        tvVersionName.setText("版本号：" + Util.getVersion(this));
    }

    @OnClick({R.id.ll_back, R.id.ln_make_appointment, R.id.ln_myorder, R.id.ln_wantcontact, R.id.ln_mymessage, R.id.ln_mystatistics, R.id.ln_exit})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            //点击返回按钮
            case R.id.ll_back:
                this.finish();
                break;
            //我要预约
            case R.id.ln_make_appointment:
//                intent.setClass(MainActivity.this,OrderActivity.class);
//                startActivity(intent);
                Util.showTost(this,"敬请期待");
                break;
            //我的单证
            case R.id.ln_myorder:
                intent.setClass(MainActivity.this,ExamineActivity.class);
                startActivity(intent);
                break;
            //联系方式
            case R.id.ln_wantcontact:
                //跳入号码输入框
                intent.setAction(Intent.ACTION_DIAL);
                //直接拨号没有提示
//                intent.setAction(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + "4008200850");
                intent.setData(data);
                startActivity(intent);
//                Util.showTost(this,"敬请期待");
                break;
            //我的消息
            case R.id.ln_mymessage:
//                intent.setClass(MainActivity.this,MyMessageActivity.class);
//                startActivity(intent);
                Util.showTost(this,"敬请期待");
                break;
            //我的统计
            case R.id.ln_mystatistics:
//                intent.setClass(MainActivity.this,MyStatisticsActivity.class);
//                startActivity(intent);
                Util.showTost(this,"敬请期待");
                break;
            //退出按钮
            case R.id.ln_exit:
                this.finish();
                break;
        }
    }

    private long firstTime = 0;

    /**
     * 双击退出
     */
    @Override
    public void onBackPressed() {
        firstTime = Util.TwiceBack(this, firstTime);
    }
}
