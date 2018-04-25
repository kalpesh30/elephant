package com.kalpeshgupta.progressbar;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    
    Button btn;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.pbst) ;
        btn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(MainActivity.this) ;
                progressDialog.setMax(100);
                progressDialog.setTitle("Sample Progress dialog");
                progressDialog.setMessage("Its loading....");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.show();
                new  Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            while(progressDialog.getProgress() <= progressDialog.getMax()) {
                                Thread.sleep(200x);
                                handle.sendMessage(handle.obtainMessage()) ;
                                if(progressDialog.getProgress() == progressDialog.getMax())
                                {
                                    progressDialog.dismiss();
                                }
                            }
                        }catch (Exception e) {
                            Log.e("Exception ->","stack trace is") ;
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            Handler handle = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    progressDialog.incrementProgressBy(1);
                }

            } ;
        });
    }
}
