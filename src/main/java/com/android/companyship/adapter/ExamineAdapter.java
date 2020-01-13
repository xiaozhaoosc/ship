package com.android.companyship.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.companyship.R;
import com.android.companyship.bean.Examinebean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2016/9/8.
 */
public class ExamineAdapter extends BaseAdapter {

    private Context context;
    private List<Examinebean> list;
    private LayoutInflater inflater;

    public ExamineAdapter(Context context, List<Examinebean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.examine_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvInspectionunmber.setText("报检号："+ list.get(position).getDeclno());
        holder.tvEnglishnameship.setText("英文船名：" + list.get(position).getShipname());
        holder.tvVoyagenumber.setText("航次号："+ list.get(position).getShipno());
        holder.tvDeliveryNumbers.setText("提单号："+ list.get(position).getCarryno());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_inspectionunmber)
        TextView tvInspectionunmber;
        @BindView(R.id.tv_englishnameship)
        TextView tvEnglishnameship;
        @BindView(R.id.tv_voyagenumber)
        TextView tvVoyagenumber;
        @BindView(R.id.tv_deliveryNumbers)
        TextView tvDeliveryNumbers;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
