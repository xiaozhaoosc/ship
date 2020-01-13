package com.android.companyship.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.companyship.R;
import com.android.companyship.bean.Examinebean;
import com.android.companyship.constant.Constant;
import com.android.companyship.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;

/**
 * Created by lenovo on 2016/9/8.
 */
public class ExamineDetailsActivity extends Activity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_titlename)
    TextView tvTitlename;
    @BindView(R.id.tv_details_inspection)
    TextView tvDetailsInspection;
    @BindView(R.id.tv_details_englishname)
    TextView tvDetailsEnglishname;
    @BindView(R.id.tv_details_voyagenumber)
    TextView tvDetailsVoyagenumber;
    @BindView(R.id.tv_details_containervolume)
    TextView tvDetailsContainervolume;
    @BindView(R.id.tv_details_deliverynumbers)
    TextView tvDetailsDeliverynumbers;
    @BindView(R.id.tv_details_boxtype)
    TextView tvDetailsBoxtype;
    @BindView(R.id.tv_details_timeappoint)
    TextView tvDetailsTimeappoint;
    @BindView(R.id.tv_details_inspectionitems)
    TextView tvDetailsInspectionitems;
    @BindView(R.id.tv_details_inspectiondepartment)
    TextView tvDetailsInspectiondepartment;
    @BindView(R.id.tv_details_sendout)
    TextView tvDetailsSendout;
    @BindView(R.id.tv_details_documentaryinfor)
    TextView tvDetailsDocumentaryinfor;
    @BindView(R.id.tv_details_arrivalstatus)
    TextView tvDetailsArrivalstatus;
    @BindView(R.id.tv_details_appointmentnotes)
    TextView tvDetailsAppointmentnotes;
    @BindView(R.id.tv_details_inspectionresult)
    TextView tvDetailsInspectionresult;
    @BindView(R.id.tv_details_operationtime)
    TextView tvDetailsOperationtime;
    @BindView(R.id.tv_details_operator)
    TextView tvDetailsOperator;
    @BindView(R.id.tv_details_inspectionNotes)
    TextView tvDetailsInspectionNotes;

    private Examinebean examinebean;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    Util.showTost(ExamineDetailsActivity.this, "请求失败，请稍后重试！");
                    break;
                case 200:
                    Util.showTost(ExamineDetailsActivity.this, "网络无连接！");
                    break;
                case 300:
                    Util.showTost(ExamineDetailsActivity.this, "请求超时！");
                    break;
                case 400:
                    String jsonStr = (String) msg.obj;
                    documentsItem(jsonStr);
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_examine_details);
        ButterKnife.bind(this);

        initData();
        dataDetail();

    }

    //初始化数据
    private void initData() {
        tvTitlename.setText("单证详情");
        examinebean = (Examinebean) getIntent().getExtras().getSerializable("Examinebean");
    }

    private void dataDetail() {

        JSONObject object = new JSONObject();
        try {
            object.put("declno",examinebean.getDeclno());
        }catch (Exception e){
            e.printStackTrace();
        }

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("method","DataDetail");
        builder.add("json",String.valueOf(object));
        Util.okThreadMenthod(ExamineDetailsActivity.this,400, Constant.startPath,builder,handler);
    }


    private void documentsItem(String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONObject item = jsonObject.getJSONArray("Table").getJSONObject(0);

            tvDetailsInspection.setText(item.getString("declno"));//报检号
            tvDetailsEnglishname.setText(item.getString("shipname"));//船名
            tvDetailsVoyagenumber.setText(item.getString("shipno"));//航次

            tvDetailsContainervolume.setText(item.getString("packageno"));//箱量
            tvDetailsDeliverynumbers.setText(item.getString("carryno"));//提单号
            tvDetailsBoxtype.setText(item.getString("iccard"));//箱子类型
            tvDetailsTimeappoint.setText(item.getString("yytime"));//预约时间

            tvDetailsInspectionitems.setText(item.getString("ready03"));//ready03  食检|木包装
            tvDetailsInspectiondepartment.setText(item.getString("ready01"));//"工业品科", //ready01

            if (item.getString("sendcyflag").equals("0")){
                tvDetailsSendout.setTextColor(Color.rgb(255,51,58));//红色
            } else if (item.getString("sendcyflag").equals("1")){
                tvDetailsSendout.setTextColor(Color.rgb(57,243,57));//绿色
            }
            tvDetailsSendout.setText(Util.judgeSiteaudit(item.getString("sendcyflag")));//计划是否发送

            if (item.getString("dd").equals("")){
                tvDetailsDocumentaryinfor.setTextColor(Color.rgb(255,51,58));//红色
                tvDetailsDocumentaryinfor.setText("未到");
            } else {
                tvDetailsDocumentaryinfor.setTextColor(Color.rgb(57,243,57));//绿色
                tvDetailsDocumentaryinfor.setText("已到");
//                tvDetailsDocumentaryinfor.setText(item.getString("dd"));//单证情况
            }

            if (item.getString("ddd").equals("未到")){
                tvDetailsArrivalstatus.setTextColor(Color.rgb(255,51,58));//红色
            } else {
                tvDetailsArrivalstatus.setTextColor(Color.rgb(57,243,57));//绿色
            }
            tvDetailsArrivalstatus.setText(item.getString("ddd"));//是否到货
            tvDetailsAppointmentnotes.setText(item.getString("spmark"));//预约备注


             if (item.getString("cyreslut").equals("1")){
                 tvDetailsInspectionresult.setTextColor(Color.rgb(57,243,57));
            } else if (item.getString("cyreslut").equals("2")){
                 tvDetailsInspectionresult.setTextColor(Color.rgb(255,51,58));
            }
//            tvDetailsInspectionresult.setText(Util.judgeRelease(item.getString("cyreslut")));//查验结果
            tvDetailsInspectionresult.setText(item.getString("cyreslut"));//查验结果
            tvDetailsOperationtime.setText(item.getString("cytime"));//查验操作时间
            tvDetailsOperator.setText(item.getString("cyman"));//查验操作人
            tvDetailsInspectionNotes.setText(item.getString("cymark1"));//查验备注
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.ll_back})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_back:
                this.finish();
                break;
        }
    }
}

