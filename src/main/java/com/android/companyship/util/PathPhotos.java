package com.android.companyship.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.text.format.DateFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by lenovo on 2016/9/9.
 */
public class PathPhotos {

    FileOutputStream fos;

    private Bitmap bitmap;

    private String fileName;

    public PathPhotos(Bitmap bitmap, String fileName) {
        this.bitmap = bitmap;
        this.fileName = fileName;
        //调用存储方法
        store();
    }

    private void store() {
        try {
            if (bitmap != null) {
                fos = new FileOutputStream(fileName);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (bitmap != null) {
                    fos.flush();
                    fos.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //设置上传照片的路径
    public static String getPhotopath() {

        DateFormat df = new DateFormat();

        String name = df.format("yyyyMMddHHmmss",
                Calendar.getInstance(Locale.CHINA)) + ".png";
        String fileName = Environment.getExternalStorageDirectory() + "/PictureImage/" + name;
        File file = new File(Environment.getExternalStorageDirectory() + "/PictureImage/");
        if (!file.exists()) {
            file.mkdirs();// 如果文件夹不存在，则创建文件夹
        }
        return fileName;
    }
}
