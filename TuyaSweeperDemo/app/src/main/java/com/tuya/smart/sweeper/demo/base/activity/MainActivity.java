package com.tuya.smart.sweeper.demo.base.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tuya.smart.sweeper.demo.R;
import com.tuya.smart.sweeper.demo.base.adapter.HomeAdapter;
import com.tuya.smart.sweeper.demo.base.bean.ItemBean;
import com.tuya.smart.sweeper.demo.base.utils.LoginHelper;
import com.tuya.smart.sweeper.demo.base.utils.ProgressUtil;
import com.tuya.smart.sweeper.demo.base.utils.ToastUtil;
import com.tuya.smart.android.user.api.ILogoutCallback;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.home.sdk.api.ITuyaHomeChangeListener;
import com.tuya.smart.home.sdk.bean.HomeBean;
import com.tuya.smart.home.sdk.callback.ITuyaGetHomeListCallback;
import com.tuya.smart.home.sdk.callback.ITuyaHomeResultCallback;
import com.tuya.smart.sdk.bean.DeviceBean;
import com.tuya.smart.sdk.bean.GroupBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private long mCurrentHomeId;
    private HomeAdapter mAdapter;
    private ITuyaHomeChangeListener mHomeChangeListener = new ITuyaHomeChangeListener() {
        @Override
        public void onHomeAdded(long l) {

        }

        @Override
        public void onHomeInvite(long homeId, String homeName) {

        }

        @Override
        public void onHomeRemoved(long l) {

        }

        @Override
        public void onHomeInfoChanged(long l) {

        }

        @Override
        public void onSharedDeviceList(List<DeviceBean> list) {

        }

        @Override
        public void onSharedGroupList(List<GroupBean> list) {

        }

        @Override
        public void onServerConnectSuccess() {
            getHomeList();
        }
    };

    public static void open(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("Sweeper");
        toolbar.inflateMenu(R.menu.toolbar_main_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.action_logout) {
                    TuyaHomeSdk.getUserInstance().logout(new ILogoutCallback() {
                        @Override
                        public void onSuccess() {
                            LoginHelper.reLogin(MainActivity.this, false);
                        }

                        @Override
                        public void onError(String errorCode, String errorMsg) {

                        }
                    });

                }
                return false;
            }
        });
        RecyclerView homeRecycler = findViewById(R.id.home_recycler);
        homeRecycler.setLayoutManager(new LinearLayoutManager(this));
        homeRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new HomeAdapter(null);
        homeRecycler.setAdapter(mAdapter);
        setListener();
        TuyaHomeSdk.getHomeManagerInstance().registerTuyaHomeChangeListener(mHomeChangeListener);
        getHomeList();
    }

    private void getHomeList() {
        ProgressUtil.showLoading(this, "Loading...");
        TuyaHomeSdk.getHomeManagerInstance().queryHomeList(new ITuyaGetHomeListCallback() {
            @Override
            public void onSuccess(List<HomeBean> list) {
                if (!list.isEmpty()) {
                    HomeBean homeBean = list.get(0);
                    mCurrentHomeId = homeBean.getHomeId();
                    getCurrentHomeDetail();
                } else {
                    ToastUtil.showToast(MainActivity.this, "家庭列表为空,请创建家庭");
                    ProgressUtil.hideLoading();
                }
            }

            @Override
            public void onError(String s, String s1) {
                ProgressUtil.hideLoading();
                Toast.makeText(MainActivity.this, s + "\n" + s1, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getCurrentHomeDetail() {
        TuyaHomeSdk.newHomeInstance(mCurrentHomeId).getHomeDetail(new ITuyaHomeResultCallback() {
            @Override
            public void onSuccess(HomeBean homeBean) {
                List<ItemBean> beans = new ArrayList<>(8);
                for (GroupBean groupBean : homeBean.getGroupList()) {
                    beans.add(getItemBeanFromGroup(groupBean));
                }
                for (DeviceBean deviceBean : homeBean.getDeviceList()) {
                    beans.add(getItemBeanFromDevice(deviceBean));
                }
                mAdapter.setData(beans);
                ProgressUtil.hideLoading();
                if(homeBean.getDeviceList()!=null && homeBean.getDeviceList().size() == 0){
                    ToastUtil.shortToast(MainActivity.this,"No devices.Please add device by other app.Like TuyaHomeSdk Demo app or your own app");
                }
            }

            @Override
            public void onError(String s, String s1) {
                ProgressUtil.hideLoading();
                Toast.makeText(MainActivity.this, s + "\n" + s1, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setListener() {

        mAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final ItemBean bean, int position) {
                Intent intent = new Intent(MainActivity.this,SweeperChooseActivity.class);
                intent.putExtra("devId",bean.getDevId());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TuyaHomeSdk.getHomeManagerInstance().unRegisterTuyaHomeChangeListener(mHomeChangeListener);
        TuyaHomeSdk.getHomeManagerInstance().onDestroy();
    }

    /**
     * 从GroupBean中获取HomeItemBean
     *
     * @param groupBean
     * @return
     */
    public ItemBean getItemBeanFromGroup(GroupBean groupBean) {
        ItemBean itemBean = new ItemBean();
        itemBean.setGroupId(groupBean.getId());
        itemBean.setTitle(groupBean.getName());
        itemBean.setIconUrl(groupBean.getIconUrl());

        List<DeviceBean> deviceBeans = groupBean.getDeviceBeans();
        if (deviceBeans == null || deviceBeans.isEmpty()) {
            return null;
        } else {
            DeviceBean onlineDev = null;
            for (DeviceBean dev : deviceBeans) {
                if (dev != null) {
                    if (dev.getIsOnline()) {
                        onlineDev = dev;
                        break;
                    } else {
                        onlineDev = dev;
                    }
                }
            }
            itemBean.setDevId(onlineDev.getDevId());
            return itemBean;
        }
    }

    /**
     * 从DeviceBean中获取HomeItemBean
     *
     * @param deviceBean
     * @return
     */
    public ItemBean getItemBeanFromDevice(DeviceBean deviceBean) {
        ItemBean itemBean = new ItemBean();
        itemBean.setDevId(deviceBean.getDevId());
        itemBean.setIconUrl(deviceBean.getIconUrl());
        itemBean.setTitle(deviceBean.getName());
        return itemBean;
    }

}
