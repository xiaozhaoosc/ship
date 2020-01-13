package com.android.companyship.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.companyship.R;
import com.android.companyship.constant.Constant;
import com.android.companyship.util.SharedPreUtil;
import com.android.companyship.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;

/**
 * Created by lenovo on 2016/9/7.
 * 登录页面
 */
public class LoginActivity extends Activity {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.sp_logintype)
    Spinner spLogintype;

    private String username = "";
    private String password = "";
    private String typritem;
    private Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
//                    Util.showTost(MainActivity.this, "操作失败，请稍后重试！");
                    break;
                case 200:
                    Util.showTost(LoginActivity.this, "网络无连接，请检查网络连接！");
                    break;
                case 300:
                    Util.showTost(LoginActivity.this, "网络连接超时，请稍后重试！");
                    break;
                case 400:
                    String jsonResult = (String) msg.obj;
                    try {
                        String nowVersion = Util.getVersion(LoginActivity.this);
                        JSONObject jsonObject = new JSONObject(jsonResult);
                        String updateVersion = jsonObject.getString("Appver");
                        String updateUrl = jsonObject.getString("Appwebpath");
                        assert nowVersion != null;
                        if (!nowVersion.equals(updateVersion)){
                            Util.showUpdataDialog(LoginActivity.this, updateUrl, jsonObject.getString("Appname"),jsonObject.getString("Appename"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    Util.showTost(LoginActivity.this, "请求失败，请稍后重试！");
                    break;
                case 200:
                    Util.showTost(LoginActivity.this, "网络无连接！");
                    break;
                case 300:
                    Util.showTost(LoginActivity.this, "请求超时！");
                    break;
                case 400:
                    String jsonStr = (String) msg.obj;
                    try {
                        JSONObject jsonObject = new JSONObject(jsonStr);
                        if (jsonStr.contains("success")){

                            SharedPreUtil.setUname(LoginActivity.this,username);
                            SharedPreUtil.setPword(LoginActivity.this,password);
                            SharedPreUtil.setTypritem(LoginActivity.this,typritem);

                            JSONObject item = jsonObject.getJSONArray("Table").getJSONObject(0);
                            if (typritem.equals("1")){
                                SharedPreUtil.setCompanynamech(LoginActivity.this,item.getString("companynamech"));
                                SharedPreUtil.setReady01(LoginActivity.this,item.getString("ready01"));
                                SharedPreUtil.setReady10(LoginActivity.this,item.getString("ready10"));
                            } else if (typritem.equals("2")){
                                SharedPreUtil.setMusername(LoginActivity.this,item.getString("musername"));
                                SharedPreUtil.setBdusername(LoginActivity.this,item.getString("bdusername"));
                                SharedPreUtil.setCUname(LoginActivity.this,item.getString("uname"));
                                SharedPreUtil.setCReady01(LoginActivity.this,item.getString("ready01"));
                                SharedPreUtil.setCReady02(LoginActivity.this,item.getString("ready02"));
                            }
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else if (jsonStr.contains("failure")){
                            Toast.makeText(LoginActivity.this, jsonObject.get("failure").toString(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
//                case 500:
//                    String jsonResult = (String) msg.obj;
//                    try {
//                        String nowVersion = Util.getVersion(LoginActivity.this);
//                        JSONObject jsonObject = new JSONObject(jsonResult);
//                        String updateVersion = jsonObject.getString("success");
//                        String updateUrl = "http://www.cargoedi.com/dangerousship.apk";
//                        assert nowVersion != null;
//                        if (!nowVersion.equals(updateVersion)) {
//                            Util.showUpdataDialog(LoginActivity.this,updateUrl);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    break;
            }
            Util.stopProgressDialog();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initDate();
        checkPermission();
    }

    //初始化数据
    private void initDate() {
        username = SharedPreUtil.getUname(LoginActivity.this);
        password = SharedPreUtil.getPword(LoginActivity.this);

        etUsername.setText(username);
        etPassword.setText(password);

//        etUsername.setText("ygf");
//        etPassword.setText("ygf");

//        String[] type = {"报检单位","车队"};
        String[] type = {"U-key","子账户"};
        ArrayAdapter<String> Adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, type);
        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLogintype.setAdapter(Adapter);
        spLogintype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                typritem = (String) parent.getItemAtPosition(position);
                typritem = position + 1 + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick({R.id.btn_login, R.id.tv_register})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            //登录
            case R.id.btn_login:
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                if (username.equals("") || password.equals("")) {
                    Util.showTost(this, "账户密码我不能为空");
                } else {
                    login();
                }

                break;
            //注册
            case R.id.tv_register:
//                intent.setClass(LoginActivity.this,RegisterActivity.class);
//                startActivity(intent);
                Util.showTost(this, "敬请期待");
                break;
        }
    }

    //点击按钮请求
    private void login() {
        Util.showProgressDialog(this,"正在登录...");
        JSONObject obj = new JSONObject();
        try {
            obj.put("username", username);
            obj.put("password", password);
            obj.put("sort",typritem);
        }catch (Exception e){
            e.printStackTrace();
        }

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("method","Applogin");
        builder.add("json", String.valueOf(obj));
        Util.okThreadMenthod(LoginActivity.this,400, Constant.startPath,builder,handler);
    }

    //版本更新
    private void checkUpdate() {
        Util.requestOkHttp(this, "weixianpin", "", updateHandler);
//        Util.showProgressDialog(LoginActivity.this,"正在检查更新...");
//        FormBody.Builder builder = new FormBody.Builder();
//        builder.add("method","GetVer");
//        Util.okThreadMenthod(LoginActivity.this,500,Constant.startPath,builder,handler);
    }

    private void checkPermission() {

        int readStoragePermissionState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int cameraPermissionState = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        boolean readStoragePermissionGranted = readStoragePermissionState != PackageManager.PERMISSION_GRANTED;
        boolean cameraPermissionGranted = cameraPermissionState != PackageManager.PERMISSION_GRANTED;

        if (readStoragePermissionGranted || cameraPermissionGranted) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                String[] permissions;
                if (readStoragePermissionGranted && cameraPermissionGranted) {
                    permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA };
                } else {
                    permissions = new String[] {
                            readStoragePermissionGranted ? Manifest.permission.READ_EXTERNAL_STORAGE
                                    : Manifest.permission.CAMERA
                    };
                }
                ActivityCompat.requestPermissions(this,
                        permissions,
                        1);
            }

        } else {
            checkUpdate();
            // Permission granted
        }

    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            // permission was granted, yay!
            checkUpdate();
        } else {
            // permission denied, boo! Disable the
            // functionality that depends on this permission.
            Toast.makeText(this, "No read storage permission! Cannot perform the action.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        switch (permission) {
            case Manifest.permission.READ_EXTERNAL_STORAGE:
            case Manifest.permission.CAMERA:
                // No need to explain to user as it is obvious
                return false;
            default:
                return true;
        }
    }
}
