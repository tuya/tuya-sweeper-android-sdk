package com.tuya.smart.sweeper.demo.base.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.android.sweeper.ITuyaByteDataListener;
import com.tuya.smart.android.sweeper.ITuyaCloudConfigCallback;
import com.tuya.smart.android.sweeper.ITuyaDelHistoryCallback;
import com.tuya.smart.android.sweeper.ITuyaSweeperByteDataListener;
import com.tuya.smart.android.sweeper.bean.SweeperByteData;
import com.tuya.smart.android.sweeper.bean.SweeperHistory;
import com.tuya.smart.android.sweeper.bean.SweeperPathBean;
import com.tuya.smart.home.sdk.callback.ITuyaResultCallback;
import com.tuya.smart.optimus.sdk.TuyaOptimusSdk;
import com.tuya.smart.optimus.sweeper.api.ITuyaSweeperKitSdk;
import com.tuya.smart.sweeper.demo.R;
import com.tuya.smart.sweeper.demo.base.utils.ToastUtil;

import butterknife.BindView;

/**
 * Laser sweeper get data and real-time data
 */
public class LaserSweeperActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "LaserSweeperActivity";

    public Button initConfigBtn;
    public Button currentMapBtn;
    public Button registerChannelBtn;
    public Button mHistoryBtn;
    public Button mDeleteBtn;

    private String devId;
    private String bucket;

    private ITuyaSweeperKitSdk iTuyaSweeperKitSdk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sweeper);

        iTuyaSweeperKitSdk = TuyaOptimusSdk.getManager(ITuyaSweeperKitSdk.class);
        devId = getIntent().getStringExtra("devId");
        init();

    }

    private void init(){

        initConfigBtn = findViewById(R.id.btn_init_config);
        currentMapBtn = findViewById(R.id.btn_current_map);
        registerChannelBtn = findViewById(R.id.btn_register_channel);
        mHistoryBtn = findViewById(R.id.btn_get_history);
        mDeleteBtn = findViewById(R.id.btn_delete_history);

        initConfigBtn.setOnClickListener(this);
        currentMapBtn.setOnClickListener(this);
        registerChannelBtn.setOnClickListener(this);
        mDeleteBtn.setOnClickListener(this);
        mHistoryBtn.setOnClickListener(this);
    }

    public void firstStep() {
        iTuyaSweeperKitSdk.getSweeperInstance().initCloudConfig(devId, new ITuyaCloudConfigCallback() {
            @Override
            public void onConfigSuccess(String bucket) {
                LaserSweeperActivity.this.bucket = bucket;
                L.d(TAG,"bucket:"+bucket);
            }

            @Override
            public void onConfigError(String errorCode, String errorMessage) {
                ToastUtil.shortToast(LaserSweeperActivity.this,"error:"+errorMessage);
            }
        });
    }

    public void secondStep() {
        iTuyaSweeperKitSdk.getSweeperInstance().getSweeperCurrentPath(devId, new ITuyaResultCallback<SweeperPathBean>() {
            @Override
            public void onSuccess(SweeperPathBean result) {
                getByteData(result.getMapPath());
                getByteData(result.getRoutePath());
            }

            @Override
            public void onError(String errorCode, String errorMessage) {
                ToastUtil.shortToast(LaserSweeperActivity.this,"error:"+errorMessage);
            }
        });
    }

    /**
     * Make sure the sweeper is cleaning,then you will get real-time data by this interface
     */
    public void thirdStep() {
        iTuyaSweeperKitSdk.getSweeperInstance().startConnectSweeperByteDataChannel(new ITuyaSweeperByteDataListener() {
            @Override
            public void onSweeperByteData(SweeperByteData data) {
                parseData(data.getData());
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtil.shortToast(LaserSweeperActivity.this,"error:"+msg);
            }
        });
    }

    public void getByteData(String relativePath){
        iTuyaSweeperKitSdk.getSweeperInstance().getSweeperByteData(bucket, relativePath, new ITuyaByteDataListener() {
            @Override
            public void onSweeperByteData(byte[] data) {
                parseData(data);
            }

            @Override
            public void onFailure(int code, String msg) {
                //If bucket expired
                updateCloudConfig();
            }
        });
    }
    private void parseData(byte[] data){
        L.d(TAG,"data length:"+data.length);
        //TODO Developers implement data analysis and draw
    }


    public void updateCloudConfig(){
        iTuyaSweeperKitSdk.getSweeperInstance().updateCloudConfig(devId, new ITuyaCloudConfigCallback() {
            @Override
            public void onConfigSuccess(String bucket) {
                LaserSweeperActivity.this.bucket = bucket;
            }

            @Override
            public void onConfigError(String errorCode, String errorMessage) {
                ToastUtil.shortToast(LaserSweeperActivity.this,"error:"+errorMessage);
            }
        });
    }
    private void deleteAllHistory() {
        iTuyaSweeperKitSdk.getSweeperInstance().deleteAllHistoryData(devId, new ITuyaDelHistoryCallback() {
            @Override
            public void onSuccess() {
                L.d(TAG,"delete success");
                ToastUtil.shortToast(LaserSweeperActivity.this,"delete success");
            }

            @Override
            public void onError(String errorCode, String errorMessage) {
                ToastUtil.shortToast(LaserSweeperActivity.this,"error:"+errorMessage);
            }
        });
    }

    private void getHistory() {

        iTuyaSweeperKitSdk.getSweeperInstance().getSweeperHistoryData(devId, 100, 0, new ITuyaResultCallback<SweeperHistory>() {
            @Override
            public void onSuccess(SweeperHistory result) {
                L.d(TAG,"totalCount :"+result.getTotalCount());
                mHistoryBtn.setText("Get History Data:"+result.getTotalCount());
            }

            @Override
            public void onError(String errorCode, String errorMessage) {
                ToastUtil.shortToast(LaserSweeperActivity.this,"error:"+errorMessage);
            }
        });
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case R.id.btn_init_config:
                firstStep();
                break;
            case R.id.btn_current_map:
                secondStep();
                break;
            case R.id.btn_register_channel:
                thirdStep();
                break;
            case R.id.btn_get_history:
                getHistory();
                break;
            case R.id.btn_delete_history:
                deleteAllHistory();
                break;
        }
    }




}
