package com.android.companyship.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.companyship.R;
import com.android.companyship.view.CalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2016/9/9.
 */
public class CalendarActivityDialog extends Activity {


    @BindView(R.id.calendarCenter)
    TextView CalendarCenter;
    @BindView(R.id.calendarLeft)
    ImageButton CalendarLeft;
    @BindView(R.id.calendarRight)
    ImageButton CalendarRight;
    @BindView(R.id.calendar)
    CalendarView calendarView;


    private SimpleDateFormat format;
    public static String[] data = {"2016928", "2016918", "2016916", "2016917", "2016920", "2016922"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.calendar_view);
        ButterKnife.bind(this);

        initData();
    }


    private void initData() {
        format = new SimpleDateFormat("yyyy-MM-dd");
        //获取日历控件对象
        calendarView.setSelectMore(false); //单选

        try {
            //设置日历日期
            Date date = format.parse("2015-01-01");
            calendarView.setCalendarData(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //获取日历中年月 ya[0]为年，ya[1]为月（格式大家可以自行在日历控件中改）
        final String[] ya = calendarView.getYearAndmonth().split("-");
        CalendarCenter.setText(ya[0] + "年" + ya[1] + "月");

        CalendarLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击上一月 同样返回年月
                String leftYearAndmonth = calendarView.clickLeftMonth();
                String[] ya = leftYearAndmonth.split("-");
                CalendarCenter.setText(ya[0] + "年" + ya[1] + "月");
            }
        });

        CalendarRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //点击下一月
                String rightYearAndmonth = calendarView.clickRightMonth();
                String[] ya = rightYearAndmonth.split("-");
                CalendarCenter.setText(ya[0] + "年" + ya[1] + "月");
            }
        });

        //设置控件监听，可以监听到点击的每一天（大家也可以在控件中根据需求设定）
        calendarView.setOnItemClickListener(new CalendarView.OnItemClickListener() {

            @Override
            public void OnItemClick(Date selectedStartDate,
                                    Date selectedEndDate, Date downDate) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMdd");
                int month = downDate.getMonth();
                int day = downDate.getDate();
                String str = sdf.format(downDate);

                Calendar calendar = Calendar.getInstance();
                int datemonth = calendar.get(Calendar.MONTH);
                int dateday = calendar.get(Calendar.DAY_OF_MONTH);

                if (month < datemonth) {
                    Toast.makeText(getApplicationContext(), "时间不正确", Toast.LENGTH_SHORT).show();
                } else if (day <= dateday) {
                    if (month <= datemonth && day <= dateday) {
                        Toast.makeText(getApplicationContext(), "时间不正确", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("detetime", format.format(downDate));
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } else {
                    for (int i = 0; i < data.length; i++) {
                        if (data[i].equals(str)) {
                            Toast.makeText(getApplicationContext(), "时间不正确", Toast.LENGTH_SHORT).show();
                            break;
                        } else if (data.length - 1 == i) {
//                            Toast.makeText(getApplicationContext(), format.format(downDate), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("detetime", format.format(downDate));
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }

                }
            }
        });
    }

}
