package com.example.map;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author : created by JTY
 * 邮箱 : 1474054587@qq.com
 * 描述 : 启动界面
 *       3000毫秒后跳转到 MainActivity
 */
public class StartActivity extends AppCompatActivity {

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //新建一个定时器
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this,MainActivity.class);
                startActivity(intent);
            }
        };
        timer.schedule(timerTask,3000);
    }
}