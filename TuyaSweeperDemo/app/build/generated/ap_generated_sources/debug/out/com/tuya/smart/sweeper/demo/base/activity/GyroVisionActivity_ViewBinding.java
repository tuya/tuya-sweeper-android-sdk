// Generated code from Butter Knife. Do not modify!
package com.tuya.smart.sweeper.demo.base.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.tuya.smart.sweeper.demo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GyroVisionActivity_ViewBinding implements Unbinder {
  private GyroVisionActivity target;

  @UiThread
  public GyroVisionActivity_ViewBinding(GyroVisionActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GyroVisionActivity_ViewBinding(GyroVisionActivity target, View source) {
    this.target = target;

    target.mLoadMapBtn = Utils.findRequiredViewAsType(source, R.id.btn_load_map, "field 'mLoadMapBtn'", Button.class);
    target.mRegisterListenerBtn = Utils.findRequiredViewAsType(source, R.id.btn_register_listener, "field 'mRegisterListenerBtn'", Button.class);
    target.mUnregisterListenerBtn = Utils.findRequiredViewAsType(source, R.id.btn_unregister_listener, "field 'mUnregisterListenerBtn'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GyroVisionActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLoadMapBtn = null;
    target.mRegisterListenerBtn = null;
    target.mUnregisterListenerBtn = null;
  }
}
