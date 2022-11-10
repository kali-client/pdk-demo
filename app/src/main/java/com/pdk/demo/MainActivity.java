package com.pdk.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import io.github.pdk.UChannelSDK;
import io.github.pdk.ICallback;

public class MainActivity extends Activity {

    private static final String KEY = "c1bd2b2ea599683c6ebcffd895bdeb4f";//key需要向ucloud云手机系统申请：申请时需提供应用包名

    private TextView tvIsCloudPhone;
    private TextView tvSendData;
    private TextView tvCallbackData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvIsCloudPhone = findViewById(R.id.tv_1);
        tvSendData = findViewById(R.id.tv_2);
        tvCallbackData = findViewById(R.id.tv_3);

        boolean isCloudPhone = UChannelSDK.getInstance(getApplication()).isCloudPhone();
        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvIsCloudPhone.setText("云手机：" + isCloudPhone);
            }
        });


        findViewById(R.id.btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap map = new HashMap();
                map.put("aaa", "111");
                map.put("bbb", "222");
                map.put("ccc", "333");
                String result = UChannelSDK.getInstance(getApplication()).sendCustomDataToClient(KEY,map);
                tvSendData.setText("返回结果：" + result); //200为发送成功
            }
        });

        findViewById(R.id.btn_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UChannelSDK.getInstance(getApplication()).register(KEY,new ICallback() {
                    @Override
                    public void callback(String key, String value) {
                        tvCallbackData.setText("value:" + value);
                    }
                });
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        UChannelSDK.getInstance(getApplication()).unregister(KEY);
    }
}