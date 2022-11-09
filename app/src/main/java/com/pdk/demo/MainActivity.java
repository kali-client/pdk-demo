package com.pdk.demo;

import static io.github.pdk.ProxyHelper.SEND_GAME_DATA;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import io.github.pdk.ICallback;
import io.github.pdk.ProxyHelper;

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

        boolean isCloudPhone = ProxyHelper.isCloudPhone(this);
        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvIsCloudPhone.setText("云手机：" + isCloudPhone);
            }
        });


        findViewById(R.id.btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = ProxyHelper.exec(getApplicationContext(), KEY, SEND_GAME_DATA, "customer_data");
                tvSendData.setText("返回结果：" + result); //200为发送成功
            }
        });

        findViewById(R.id.btn_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProxyHelper.register(getApplicationContext(), KEY, new ICallback() {
                    @Override
                    public void callback(String key, String value) {
                        tvCallbackData.setText("key:" + key + " value:" + value);
                    }
                });
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ProxyHelper.unregister(getApplicationContext(), KEY);
    }
}