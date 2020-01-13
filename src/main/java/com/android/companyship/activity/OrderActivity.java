package com.android.companyship.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.companyship.R;
import com.android.companyship.bean.AppointBean;
import com.android.companyship.view.ListViewForScrollView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2016/9/7.
 * <p/>
 * 我要预约页面
 */
public class OrderActivity extends Activity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_titlename)
    TextView tvTitlename;
    @BindView(R.id.et_order_inspectionnumber)
    EditText etOrderInspectionnumber;
    @BindView(R.id.iv_order_search)
    ImageView ivOrderSearch;
    @BindView(R.id.et_order_englishname)
    EditText etOrderEnglishname;
    @BindView(R.id.et_order_voyagenumber)
    EditText etOrderVoyagenumber;
    @BindView(R.id.et_order_billlading)
    EditText etOrderBilllading;
    @BindView(R.id.sp_order_boxtype)
    Spinner spOrderBoxtype;
    @BindView(R.id.sp_order_inspectiondepartment)
    Spinner spOrderInspectiondepartment;
    @BindView(R.id.sp_order_destination)
    Spinner spOrderDestination;
    @BindView(R.id.et_order_containervolume)
    EditText etOrderContainervolume;
    @BindView(R.id.tv_order_timeappoint)
    TextView tvOrderTimeappoint;
    @BindView(R.id.sp_order_inspectionsite)
    Spinner spOrderInspectionsite;
    @BindView(R.id.cb_order_checkitem1)
    CheckBox cbOrderCheckitem1;
    @BindView(R.id.cb_order_checkitem2)
    CheckBox cbOrderCheckitem2;
    @BindView(R.id.cb_order_checkitem3)
    CheckBox cbOrderCheckitem3;
    @BindView(R.id.cb_order_checkitem4)
    CheckBox cbOrderCheckitem4;
    @BindView(R.id.cb_order_checkitem5)
    CheckBox cbOrderCheckitem5;
    @BindView(R.id.cb_order_checkitem6)
    CheckBox cbOrderCheckitem6;
    @BindView(R.id.cb_order_checkitem7)
    CheckBox cbOrderCheckitem7;
    @BindView(R.id.cb_order_checkitem8)
    CheckBox cbOrderCheckitem8;
    @BindView(R.id.et_order_contacts)
    EditText etOrderContacts;
    @BindView(R.id.et_order_contactnumber)
    EditText etOrderContactnumber;
    @BindView(R.id.et_order_remarks)
    EditText etOrderRemarks;
    @BindView(R.id.tv_order_savedata)
    TextView tvOrderSavedata;


    @BindView(R.id.et_order_fightinspection)
    EditText etOrderFightinspection;
    @BindView(R.id.tv_order_addtoitem)
    TextView tvOrderAddtoitem;
    @BindView(R.id.lv_addlistview)
    ListView lvAddlistview;
    @BindView(R.id.sp_order_boxnumber)
    Spinner spOrderBoxnumber;
    @BindView(R.id.sp_order_boxshape)
    Spinner spOrderBoxshape;
    @BindView(R.id.sp_order_boxsize)
    Spinner spOrderBoxsize;
    @BindView(R.id.et_order_nationsnumber)
    EditText etOrderNationsnumber;
    @BindView(R.id.sp_order_risklevel)
    Spinner spOrderRisklevel;
    @BindView(R.id.tv_order_increase)
    TextView tvOrderIncrease;
    @BindView(R.id.lv_listincrease)
    ListViewForScrollView lvListincrease;

    private List<AppointBean> caselist = new ArrayList<>();
    private CasenumberAdapter adapter;
    private int mYear; //定义时间年
    private String date; //显示
    private PopupWindow popedate;
    private SimpleDateFormat format;

    private String dateappoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        addData();
        initData();
        initSpinn();
    }

    private void addData() {
        AppointBean appointBean;
        for (int i=0;i < 4;i++){
            appointBean = new AppointBean();
            appointBean.setBoxnumber("3454325");
            appointBean.setBoxshape("TK");
            appointBean.setBoxsize("40");
            appointBean.setNationsnumber("345345");
            appointBean.setRisklevel("ewfef");
            caselist.add(appointBean);
        }
    }

    //初始化数据
    private void initData() {
        tvTitlename.setText("我要预约");

        adapter = new CasenumberAdapter(this,caselist);
        lvListincrease.setAdapter(adapter);
//        Calendar calendar1 = Calendar.getInstance();
//        mYear = calendar1.get(Calendar.YEAR);
//        int mMouth = calendar1.get(Calendar.MONTH);
//        int mDate = calendar1.get(Calendar.DAY_OF_MONTH);
//        changeDate(mMouth, mDate);
        tvOrderTimeappoint.setText("请选择");

    }

    //初始化下拉数据
    private void initSpinn() {

        //箱子类型
        String[] n = {"疏港箱", "外来箱", "外来散货"};

        //施检部门
        String[] m = {"工业品科", "临港科", "查验科", "卫食科", "查验四科", "查验一科",
                "查验一颗(危库)", "查验二科", "工业品科", "检务科", "工业品科"};

        //目的地机构
        String[] l = {"洋山局", "南汇局", "松江局", "浦东局本部", "外高桥局",
                "外高桥保税区办事处", "洋山局本部", "昆山局"};

        //查验地点
        String[] o = {"港城危化库"};

        //箱号
        String[] r = {"1", "2"};

        //箱形
        String[] s = {"TK", "GP", "RF"};

        //尺寸
        String[] y = {"45", "20", "40"};

        //危险等级
        String[] z = {"1.1", "1.2", "1.3", "1.4", "1.5", "2.1", "2.2", "2.3", "3",
                "3.1", "3.2", "3.3", "4.1", "4.2", "4.3", "普货", "5.1", "5.2", "6.1", "6.2"};

        //箱子类型
        ArrayAdapter<String> Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, n);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOrderBoxtype.setAdapter(Adapter);
        spOrderBoxtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String OrderBoxtype = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //施检部门
        ArrayAdapter<String> Adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, m);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOrderInspectiondepartment.setAdapter(Adapter1);
        spOrderInspectiondepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String OrderInspectiondepartment = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //目的地机构
        ArrayAdapter<String> Adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, l);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOrderDestination.setAdapter(Adapter2);
        spOrderDestination.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String OrderDestination = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //查验地点
        ArrayAdapter<String> Adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, o);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOrderInspectionsite.setAdapter(Adapter3);
        spOrderInspectionsite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String OrderInspectionsite = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> Adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, r);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOrderBoxnumber.setAdapter(Adapter4);
        spOrderBoxnumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String orderBoxnumber = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> Adapter5 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, s);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOrderBoxshape.setAdapter(Adapter5);
        spOrderBoxshape.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String orderBoxshape = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> Adapter6 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, y);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOrderBoxsize.setAdapter(Adapter6);
        spOrderBoxsize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String orderBoxnumber = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> Adapter7 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, z);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOrderRisklevel.setAdapter(Adapter7);
        spOrderRisklevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String orderRisklevel = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @OnClick({R.id.ll_back, R.id.iv_order_search, R.id.tv_order_savedata, R.id.tv_order_timeappoint,
            R.id.tv_order_addtoitem, R.id.tv_order_increase})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            //返回上一页面
            case R.id.ll_back:
                this.finish();
                break;
            //点击查询
            case R.id.iv_order_search:
                Toast.makeText(OrderActivity.this,"查询",Toast.LENGTH_SHORT).show();
                break;
            //点击保存
            case R.id.tv_order_savedata:
                Toast.makeText(OrderActivity.this,"保存数据",Toast.LENGTH_SHORT).show();
                break;
            //点击选择时间
            case R.id.tv_order_timeappoint:
//                Util.showData(OrderActivity.this,tvOrderTimeappoint);
                intent.setClass(OrderActivity.this, CalendarActivityDialog.class);
                startActivityForResult(intent, 1);
//                showDatePopup(view);
                break;
            //添加按钮
            case R.id.tv_order_addtoitem:
                Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
                break;
            //增加按钮
            case R.id.tv_order_increase:
                Toast.makeText(this,"增加成功",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case 1:
                    dateappoint = data.getStringExtra("detetime");
                    tvOrderTimeappoint.setText(dateappoint);
                    break;
            }
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

    //拼票数据
    class TicketAdapter extends BaseAdapter {

        private Context context;
        private List<String> ticketlist;

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }


    //箱号列表数据
    class CasenumberAdapter extends BaseAdapter {

        private Context context;
        private List<AppointBean> caselist;
        private LayoutInflater inflater;

        public CasenumberAdapter(Context context, List<AppointBean> caselist) {
            this.context = context;
            this.caselist = caselist;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return caselist.size();
        }

        @Override
        public Object getItem(int position) {
            return caselist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.case_itemview, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvItemboxnumber.setText("箱号："+caselist.get(position).getBoxnumber());
            holder.tvItemboxshape.setText("箱形："+caselist.get(position).getBoxshape());
            holder.tvItemboxsize.setText("尺寸："+caselist.get(position).getBoxsize());
            holder.tvItemnationsnumber.setText("联合国编号："+caselist.get(position).getNationsnumber());
            holder.tvItemrisklevel.setText("危险类别："+caselist.get(position).getRisklevel());
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.tv_itemboxnumber)
            TextView tvItemboxnumber;
            @BindView(R.id.tv_itemboxshape)
            TextView tvItemboxshape;
            @BindView(R.id.tv_itemboxsize)
            TextView tvItemboxsize;
            @BindView(R.id.tv_itemnationsnumber)
            TextView tvItemnationsnumber;
            @BindView(R.id.tv_itemrisklevel)
            TextView tvItemrisklevel;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
