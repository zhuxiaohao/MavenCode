package com.cn.reading;

import com.cn.reading.zxing.activity.CaptureActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Project Name:com.cn.reading
 * File Name: Reading
 * Date:15/8/28下午4:2008
 * blog:http://blog.csdn.net/qq718799510?viewmode=contents
 * Copyright (c) 2015, zhuxiaohao All Rights Reserved.
 */
public class MainActivity extends Activity  implements OnClickListener{

    Button btn1,btn2;
    ImageView imageView;
    android.widget.TextView resultTexyView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1=(android.widget.Button)findViewById(com.cn.reading.R.id.btn1);
        btn2=(android.widget.Button)findViewById(com.cn.reading.R.id.btn2);
        imageView=(ImageView)findViewById(R.id.imageView);
        resultTexyView=(android.widget.TextView)findViewById(R.id.resultTexyView);
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v
     *         The view that was clicked.
     */
    @Override
    public void onClick(android.view.View v) {

        if (btn1==v){
            startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class),0);
        }
        if (btn2==v){
            String str="www.baidu.com";
            try {
                android.graphics.Bitmap qrCodeBitmap= com.cn.reading.zxing.encoding.EncodingHandler.createQRCode(str, 350);
                imageView.setImageBitmap(qrCodeBitmap);
            } catch (com.google.zxing.WriterException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if (resultCode==RESULT_OK){
           Bundle bundle=data.getExtras();
           String scanResult=bundle.getString("result");
           resultTexyView.setText(scanResult);
       }
    }
}
