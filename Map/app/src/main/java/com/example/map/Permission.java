package com.example.map;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

/**
 * @author : created by JTY
 * 邮箱 : 1474054587@qq.com
 * 描述 : 动态申请权限
 */
public class Permission {
    private String[] permissions = {
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private ArrayList<String> permissionList = new ArrayList<>();
    private int requestCode = 1000;

    /**
     * 有参构造
     * @param activity 调用权限类传入自身
     */
    public Permission(Activity activity){
        //判断安卓版本，旧版安卓无需动态申请权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //遍历权限，如缺少权限则加入 permissionList
            for (int i = 0; i < permissions.length; i++) {
                if (ContextCompat.checkSelfPermission(activity, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(permissions[i]);
                }
            }
            //非空说明缺少权限，开始申请
            if (permissionList.size() > 0) {
                request(activity);
            }
        }
    }

    /**
     * 动态申请 permissionList 中的所有权限
     * @param activity
     */
    private void request (Activity activity){
        ActivityCompat.requestPermissions(activity,permissionList.toArray(new String[permissionList.size()]),requestCode);
    }
}
