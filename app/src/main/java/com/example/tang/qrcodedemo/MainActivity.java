package com.example.tang.qrcodedemo;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.tang.qrcodedemo.permission.MPermission;
import com.example.tang.qrcodedemo.permission.annotation.OnMPermissionDenied;
import com.example.tang.qrcodedemo.permission.annotation.OnMPermissionGranted;
import com.example.tang.qrcodedemo.permission.annotation.OnMPermissionNeverAskAgain;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int BASIC_PERMISSION_REQUEST_CODE = 1000;
    private static final int RQCODE_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_main_qrCode).setOnClickListener(this);
    }

    /**
     * 基本权限管理
     */
    private void requestBasicPermission(String permission) {
        MPermission.with(MainActivity.this)
                .addRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                .permissions(
                        permission
                ).request();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RQCODE_REQUEST_CODE://扫描结果
                    String result = data.getStringExtra("result");
                    Log.d("TAG", "onActivityResult: 扫描结果====  " + result);
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case BASIC_PERMISSION_REQUEST_CODE://请求权限
                MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
                break;
        }
    }

    //同意
    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnMPermissionGranted(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionSuccess() {
        Intent intent = new Intent(this, QRCodeActivity.class);
        startActivityForResult(intent, RQCODE_REQUEST_CODE);
    }

    //拒绝
    @OnMPermissionDenied(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionFailed() {

    }

    //不再提醒
    @OnMPermissionNeverAskAgain(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionNeverAskAgain() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_main_qrCode://扫描二维码
                requestBasicPermission(Manifest.permission.CAMERA);
                break;
        }
    }
}
