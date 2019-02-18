package com.example.tang.qrcodedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class QRCodeActivity extends AppCompatActivity implements QRCodeView.Delegate {
    private ZXingView mZXingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        mZXingView = findViewById(R.id.zxing);
        mZXingView.startCamera();
        //设置扫描代理，处理扫描结果
        mZXingView.setDelegate(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mZXingView.startSpot();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mZXingView.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mZXingView.onDestroy();
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    /**
     * 扫描成功
     *
     * @param result
     */
    @Override
    public void onScanQRCodeSuccess(String result) {
        Toast.makeText(this, "扫描结果： " + result, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.putExtra("result", result);
        setResult(RESULT_OK, intent);
        finish();

    }

    /**
     * 摄像头环境亮度发生变化
     *
     * @param isDark 是否变暗
     */
    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
//        if (isDark) {//环境变暗
//            Toast.makeText(this, "环境变得太暗了", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "环境又变亮了", Toast.LENGTH_SHORT).show();
//
//        }
    }

    /**
     * 处理打开相机出错
     */
    @Override
    public void onScanQRCodeOpenCameraError() {

    }
}
