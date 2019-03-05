### 扫地机SDK集成说明

**（1）使用 tuyasmart-sweeper-0.0.1.aar 替换已集成的涂鸦智能SDK**

**（2）修改 AndroidManifest.xml 中 <service/> 的配置项，将原有的 <service/> 配置改为如下：**

```
<service
    android:name="com.tuya.smart.android.hardware.service.DevTransferService"
    android:process=":monitor"
    android:stopWithTask="true" />
<service
    android:name="com.tuya.smart.android.hardware.service.GwBroadcastMonitorService"
    android:exported="true"
    android:label="UDPService"
    android:process=":monitor"
    android:stopWithTask="true">
    <intent-filter>
        <action android:name="tuya.intent.action.udp" />
        <category android:name="tuya" />
    </intent-filter>
</service>
<service
    android:name="com.tuya.smart.mqtt.MqttService"
    android:stopWithTask="true" />
```

**（3）依赖与混淆配置的修改**

如果您的项目中不需要用到 **RxJava** 或 **Eventbus**，则可以将该依赖移除，

同时将  **RxJava** 或 **Eventbus** 的混淆配置移除