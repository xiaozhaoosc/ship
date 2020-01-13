package com.android.companyship.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.companyship.R;
import com.android.companyship.util.PathPhotos;
import com.android.companyship.util.PictureCompUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2016/9/7.
 * 注册页面
 */
public class RegisterActivity extends Activity {
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_titlename)
    TextView tvTitlename;
    @BindView(R.id.tv_rightBtn)
    TextView tvRightBtn;
    @BindView(R.id.rlMainTitle)
    RelativeLayout rlMainTitle;
    @BindView(R.id.et_enterprise_namechia)
    EditText etEnterpriseNamechia;
    @BindView(R.id.et_enterprise_nameeng)
    EditText etEnterpriseNameeng;
    @BindView(R.id.et_business_address)
    EditText etBusinessAddress;
    @BindView(R.id.et_zipcode)
    EditText etZipcode;
    @BindView(R.id.et_organization_code)
    EditText etOrganizationCode;
    @BindView(R.id.et_declaration_code)
    EditText etDeclarationCode;
    @BindView(R.id.et_contacts)
    EditText etContacts;
    @BindView(R.id.et_contact_number)
    EditText etContactNumber;
    @BindView(R.id.iv_business_license)
    ImageView ivBusinessLicense;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_refillings)
    Button btnRefillings;

    private String fileName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        initDate();
    }

    //初始化数据
    private void initDate() {
        tvTitlename.setText("企业信息注册");
    }

    @OnClick({R.id.ll_back, R.id.iv_business_license,
            R.id.btn_register, R.id.btn_refillings})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            //营业执照
            case R.id.iv_business_license:
                showImageDialog(this);
                break;
            //用户名注册
            case R.id.btn_register:
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                break;
            //清空数据
            case R.id.btn_refillings:
                etEnterpriseNamechia.setText("");
                etEnterpriseNameeng.setText("");
                etBusinessAddress.setText("");
                etZipcode.setText("");
                etOrganizationCode.setText("");
                etDeclarationCode.setText("");
                etContacts.setText("");
                etContactNumber.setText("");
                ivBusinessLicense.setImageResource(R.drawable.photo_idcard);
                break;
        }
    }

    private void showImageDialog(RegisterActivity registerActivity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(registerActivity);
        builder.setTitle("添加图片");
        builder.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //获取照片的保存地址
                fileName = PathPhotos.getPhotopath();

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                File out = new File(fileName);
                Uri uri = Uri.fromFile(out);
                // 获取拍照后未压缩的原图片，并保存在uri路径中
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 1);
            }
        });
        builder.setNeutralButton("相册", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 2);
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1) {
            //这里是先压缩质量，再调用存储方法
            Bitmap bitmap = PictureCompUtil.getimage(fileName);
            new PathPhotos(bitmap, fileName);
            ivBusinessLicense.setImageBitmap(bitmap);

        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap bitmap = PictureCompUtil.getimage(picturePath);
            //获取图片并显示
            ivBusinessLicense.setImageBitmap(bitmap);
        }
    }
}
