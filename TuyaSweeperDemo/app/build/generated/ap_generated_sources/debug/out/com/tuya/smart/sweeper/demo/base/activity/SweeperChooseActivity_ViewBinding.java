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

public class SweeperChooseActivity_ViewBinding implements Unbinder {
  private SweeperChooseActivity target;

  @UiThread
  public SweeperChooseActivity_ViewBinding(SweeperChooseActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SweeperChooseActivity_ViewBinding(SweeperChooseActivity target, View source) {
    this.target = target;

    target.gyroBtn = Utils.findRequiredViewAsType(source, R.id.btn_gyro, "field 'gyroBtn'", Button.class);
    target.laserBtn = Utils.findRequiredViewAsType(source, R.id.btn_laser, "field 'laserBtn'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SweeperChooseActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.gyroBtn = null;
    target.laserBtn = null;
  }
}
