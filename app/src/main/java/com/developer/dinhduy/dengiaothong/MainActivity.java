package com.developer.dinhduy.dengiaothong;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mTxt1,mTxt_Green,mTxt_Red;
    private LinearLayout mlayout;
    private Handler mhander;
    private int mTime;
    private int Time_GREEN=40;
    private int Time_RED=60;
    // time yellow Same  ok
    private String TAG="365";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mlayout=(LinearLayout) findViewById(R.id.id_layout);
        mTxt1=(TextView) findViewById(R.id.id_Time_Count);
        mTxt_Green=(TextView) findViewById(R.id.id_time_green);
        mTxt_Red=(TextView) findViewById(R.id.id_time_red);

        //Run method
        init();
        update();

    }
    //ok now setup backgound color
    private void init(){
        mhander=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==1998) {
                    Log.d(TAG, " " + msg.arg1 + "S");
                    mTxt1.setText(msg.arg1 + " S");
                    if(msg.arg2<0){
                        mTxt_Green.setText("Time Green :0S");
                    }else {
                        mTxt_Green.setText("Time Green :"+msg.arg2+"S");
                    }
                    if (msg.arg1 > 60) {
                        if (msg.arg1 == 65) {
                            mTime = 0;
                        }
                        mlayout.setBackgroundColor(Color.YELLOW);
                        Log.d(TAG, "Backgound yellow: ");
                    }
                    //set YEALOW
                    else if (msg.arg1 >= 1 && msg.arg1 <= 40) {
                        //set backgound GREEN
                        mlayout.setBackgroundColor(Color.GREEN);
                        Log.d(TAG, "Backgound GREEN: ");
                    } else if (msg.arg1 > 40 && msg.arg1 <= 60) {
                        //set RED
                        mlayout.setBackgroundColor(Color.RED);
                        Log.d(TAG, "Backgound RED: ");
                    }

                }

                if(msg.what==1997){
                    if(msg.arg1<0){
                        mTxt_Red.setText("Time RED :0S");
                    }else if(mTime>40&&mTime<=60){
                        mTxt_Red.setText("Time RED :"+msg.arg1+"S");
                    }else {
                        mTxt_Red.setText("Time RED :0S");
                    }
                }
                super.handleMessage(msg);
            }
        };

    }
    private void update(){
        Thread thread=new Thread(){
            @Override
            public void run() {
                while (true){
                    //workking count
                    mTime+=1;
                    Message message1=new Message();
                    message1.what=1998;
                    message1.arg1=mTime;
                    message1.arg2=Time_GREEN-mTime;
                    mhander.sendMessage(message1);

                    //setup messege 2
                    Message message2=new Message();
                    message2.what=1997;
                    message2.arg1=Time_RED-mTime;
                    mhander.sendMessage(message2);
                    //sleep 1 Second
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }


            }
        };
        thread.start();
    }
}
