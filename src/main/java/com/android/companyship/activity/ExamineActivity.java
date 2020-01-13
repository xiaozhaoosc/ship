package com.android.companyship.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.companyship.R;
import com.android.companyship.adapter.ExamineAdapter;
import com.android.companyship.bean.Examinebean;
import com.android.companyship.constant.Constant;
import com.android.companyship.util.SharedPreUtil;
import com.android.companyship.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;

/**
 * Created by lenovo on 2016/9/8.
 * 我的单证页面
 */
public class ExamineActivity extends Activity implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, AbsListView.OnScrollListener {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_titlename)
    TextView tvTitlename;
    @BindView(R.id.iv_listexamine)
    ImageView ivListexamine;
    @BindView(R.id.ll_listexamine)
    LinearLayout llListexamine;
    @BindView(R.id.lv_examinelist)
    ListView lvExaminelist;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.swiprefresh)
    SwipeRefreshLayout swiprefresh;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;
    @BindView(R.id.ln_nodata)
    LinearLayout lnNodata;

    private String sendflag;
    private String cyresult;
    private int curPage = 1;
    private int totalCount;

    private PopupWindow popexamine;
    private List<Examinebean> list = new ArrayList<>();
    private ExamineAdapter adapter;
    private int mYear; //定义时间年
    private String date; //显示

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    lnNodata.setVisibility(View.VISIBLE);
                    tvNodata.setText("请求失败，请稍后重试！");
                    break;
                case 200:
                    lnNodata.setVisibility(View.VISIBLE);
                    tvNodata.setText("网络无连接！");
//                    Util.showTost(ExamineActivity.this, "网络无连接！");
                    break;
                case 300:
                    lnNodata.setVisibility(View.VISIBLE);
                    tvNodata.setText("请求超时！");
//                    Util.showTost(ExamineActivity.this, "请求超时！");
                    break;
                case 400:
                    String jsonStr = (String) msg.obj;
                    documentsItem(jsonStr);
                    adapter.notifyDataSetChanged();
                    break;
            }
            swiprefresh.setRefreshing(false);
//            Util.stopProgressDialog();
            progressBar.setVisibility(View.GONE);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_examine);
        ButterKnife.bind(this);

        initData();
        documentsData();

    }

    private void documentsData() {
//        Util.showProgressDialog(this, "正在登陆...");
//        progressBar.setVisibility(View.VISIBLE);
        JSONObject obj = new JSONObject();
        try {
            obj.put("username", SharedPreUtil.getUname(ExamineActivity.this));
            obj.put("usersort", SharedPreUtil.getTypritem(ExamineActivity.this));
            obj.put("declno", "");
            obj.put("carryno", "");
            obj.put("yytime1", "");
            obj.put("yytime2", "");
            obj.put("sendflag", "");
            obj.put("cyresult", "");
            obj.put("CurPage",curPage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("method", "DataSearch");
        builder.add("json", String.valueOf(obj));
        Util.okThreadMenthod(ExamineActivity.this, 400, Constant.startPath, builder, handler);
    }

    private void documentsItem(String jsonobjStr) {
        try {
            JSONObject object = new JSONObject(jsonobjStr);
            totalCount = object.getInt("count");
            JSONArray jsonArray = object.getJSONArray("Table");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                Examinebean examinebean = new Examinebean();
                examinebean.setDeclno(item.getString("declno"));
                examinebean.setShipname(item.getString("shipname"));
                examinebean.setShipno(item.getString("shipno"));
                examinebean.setCarryno(item.getString("carryno"));
                list.add(examinebean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //初始化数据
    private void initData() {
        tvTitlename.setText("我的单证");
        adapter = new ExamineAdapter(this, list);
        lvExaminelist.setAdapter(adapter);
        lvExaminelist.setOnItemClickListener(this);
        swiprefresh.setOnRefreshListener(this);
        lvExaminelist.setOnScrollListener(this);
    }

    @OnClick({R.id.ll_back, R.id.ll_listexamine})
    public void onClick(View view) {
        switch (view.getId()) {
            //点击返回
            case R.id.ll_back:
                this.finish();
                break;
            //点击查询结果
            case R.id.ll_listexamine:
                examinePopupWindow(view);
                break;
        }
    }

    private void examinePopupWindow(View view) {
        ivListexamine.setBackgroundResource(R.drawable.pop_up);
        if (popexamine == null) {
            popexamine = new PopupWindow(ExamineActivity.this);
        }
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertview = inflater.inflate(R.layout.list_item_examine, null);
        popexamine.setContentView(convertview);
        popexamine.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ivListexamine.setBackgroundResource(R.drawable.pop_down);
            }
        });
        popexamine.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popexamine.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popexamine.showAsDropDown(view, 10, 10);
        popexamine.setFocusable(true);
        popexamine.setOutsideTouchable(true);
        // 刷新状态
        popexamine.update();

        final ViewHolder holder = new ViewHolder(convertview);

        //计划是否发送
        String[] n = {"请选择...", "已发送", "未发送"};

        //是否放行
        String[] m = {"请选择...", "未操作", "已放行", "未放行"};

        holder.tvStartDate.setText("");
        holder.tvEnddate.setText("");

        //审核结果
//        ArrayAdapter<String> Adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, n);
//        Adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, n);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        holder.spDelivery.setAdapter(adapter);
        holder.spDelivery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //sendflag = (String) adapterView.getItemAtPosition(i);
                if (position == 0) {
                    sendflag = "";
                } else {
                    sendflag = position - 1 + "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);//设置下拉框条目样式
        holder.spRelease.setAdapter(adapter1);
        holder.spRelease.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //cyresult = (String) parent.getItemAtPosition(position);
                if (position == 0) {
                    cyresult = "";
                } else {
                    cyresult = position - 1 + "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.showData(ExamineActivity.this, holder.tvStartDate);
            }
        });

        holder.tvEnddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.showData(ExamineActivity.this, holder.tvEnddate);
            }
        });

        holder.tvQueryexamine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                querylist(holder);
                popexamine.dismiss();
            }
        });


    }

    //点击查询接口
    private void querylist(ViewHolder holder) {
//        progressBar.setVisibility(View.VISIBLE);
        JSONObject obj = new JSONObject();
        try {
            obj.put("username", SharedPreUtil.getUname(ExamineActivity.this));
            obj.put("usersort", SharedPreUtil.getTypritem(ExamineActivity.this));
            obj.put("declno", holder.etReportno.getText().toString());
            obj.put("carryno", holder.etDeliverynumbers.getText().toString());
            obj.put("yytime1", holder.tvStartDate.getText().toString());
            obj.put("yytime2", holder.tvEnddate.getText().toString());
            obj.put("sendflag", sendflag);
            obj.put("cyresult", cyresult);
            obj.put("CurPage","1");
        } catch (Exception e) {
            e.printStackTrace();
        }

        list.clear();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("method", "DataSearch");
        builder.add("json", String.valueOf(obj));
        Util.okThreadMenthod(ExamineActivity.this, 400, Constant.startPath, builder, handler);
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

        if (mMouth < 9) {
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


    //点击条目查看详情
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(ExamineActivity.this, ExamineDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Examinebean", list.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //上拉加载
    @Override
    public void onRefresh() {
        list.clear();
        adapter.notifyDataSetChanged();
        curPage = 1;
        documentsData();
    }

    //下拉刷新
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (last == total && scrollState == SCROLL_STATE_IDLE) {
            int totalPages = totalCount / 20;
            if (totalCount % 20 > 0) {
                totalPages++;
            }
            if (curPage < totalPages) {
                curPage++;
                documentsData();
            } else {
                Toast.makeText(this, "已经到最后一页了~~", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        last = firstVisibleItem + visibleItemCount;
        total = totalItemCount;
    }

    int last;
    int total;


    class ViewHolder {

        @BindView(R.id.et_reportno)
        EditText etReportno;
        @BindView(R.id.et_deliverynumbers)
        EditText etDeliverynumbers;
        @BindView(R.id.tv_startDate)
        TextView tvStartDate;
        @BindView(R.id.tv_enddate)
        TextView tvEnddate;
        @BindView(R.id.sp_delivery)
        Spinner spDelivery;
        @BindView(R.id.sp_release)
        Spinner spRelease;
        @BindView(R.id.tv_queryexamine)
        TextView tvQueryexamine;

        public ViewHolder(View convertview) {
            ButterKnife.bind(this, convertview);
        }
    }
}
