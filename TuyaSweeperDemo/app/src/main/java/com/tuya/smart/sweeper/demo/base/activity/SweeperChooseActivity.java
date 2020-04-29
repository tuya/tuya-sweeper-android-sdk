package com.tuya.smart.sweeper.demo.base.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.tuya.smart.sweeper.demo.R;

import butterknife.BindView;

public class SweeperChooseActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_gyro)
    Button gyroBtn;
    @BindView(R.id.btn_laser)
    Button laserBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sweeper_choose);


        gyroBtn = findViewById(R.id.btn_gyro);
        laserBtn = findViewById(R.id.btn_laser);

        gyroBtn.setOnClickListener(this);
        laserBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.btn_gyro:
                intent.setClass(this,GyroVisionActivity.class);
                intent.putExtra("devId",getIntent().getStringExtra("devId"));
                startActivity(intent);
                break;
            case R.id.btn_laser:
                intent.setClass(this,LaserSweeperActivity.class);
                intent.putExtra("devId",getIntent().getStringExtra("devId"));
                startActivity(intent);
                break;
        }
    }
}
