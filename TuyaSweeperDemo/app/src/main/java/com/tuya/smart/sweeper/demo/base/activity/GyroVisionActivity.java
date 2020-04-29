package com.tuya.smart.sweeper.demo.base.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tuya.smart.android.common.utils.L;
import com.tuya.smart.sweeper.demo.R;
import com.tuya.smart.sweeper.demo.base.bean.MapDataBean;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.home.sdk.bean.TransferDataBean;
import com.tuya.smart.home.sdk.callback.ITuyaTransferCallback;
import com.tuya.smart.sdk.api.ITuyaDataCallback;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * The interfaces  of gyro or vision sweeper call process
 *
 */
public class GyroVisionActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "GyroVisionActivity";

    @BindView(R.id.btn_load_map)
    Button mLoadMapBtn;
    @BindView(R.id.btn_register_listener)
    Button mRegisterListenerBtn;
    @BindView(R.id.btn_unregister_listener)
    Button mUnregisterListenerBtn;

    private String devId;

    private ITuyaDataCallback<TransferDataBean> iTuyaDataCallback = new ITuyaDataCallback<TransferDataBean>() {
        @Override
        public void onSuccess(TransferDataBean result) {
            parseData();
        }

        @Override
        public void onError(String errorCode, String errorMessage) {

        }
    };
    private ITuyaTransferCallback iTuyaTransferCallback = new ITuyaTransferCallback() {
        @Override
        public void onConnectSuccess() {
            L.d(TAG,"mqtt connect success");
        }

        @Override
        public void onConnectError(String code, String error) {

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyro_vision);

        devId = getIntent().getStringExtra("devId");

        init();
    }

    private void init() {
        mLoadMapBtn.setOnClickListener(this);
        mRegisterListenerBtn.setOnClickListener(this);
        mUnregisterListenerBtn.setOnClickListener(this);
    }


    private void firstStep() {
        Map<String,Object> data = new HashMap<>();
        data.put("devId",devId);
        data.put("subRecordId",1);
        data.put("start","");
        data.put("size","500");
        TuyaHomeSdk.getRequestInstance().requestWithApiName("tuya.m.device.media.detail", "1.0", data, MapDataBean.class, new ITuyaDataCallback<MapDataBean>() {
            @Override
            public void onSuccess(MapDataBean result) {
                parseData();
            }

            @Override
            public void onError(String errorCode, String errorMessage) {

            }
        });
    }

    private void secondStep() {
        TuyaHomeSdk.getTransferInstance().registerTransferDataListener(iTuyaDataCallback);

        TuyaHomeSdk.getTransferInstance().startConnect();

        // optional method.If MQTT is disconnected,the SDK will auto try to reconnect
        TuyaHomeSdk.getTransferInstance().registerTransferCallback(iTuyaTransferCallback);
    }


    private void thirdStep(){
        TuyaHomeSdk.getTransferInstance().unRegisterTransferCallback(iTuyaTransferCallback);
        TuyaHomeSdk.getTransferInstance().unRegisterTransferDataListener(iTuyaDataCallback);
    }


    private void parseData(){
        //TODO Developers implement data analysis and draw
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_load_map:
                firstStep();
                break;
            case R.id.btn_register_listener:
                secondStep();
                break;
            case R.id.btn_unregister_listener:
                thirdStep();
                break;

        }
    }




}
