// Generated code from Butter Knife. Do not modify!
package com.tuya.smart.sweeper.demo.login.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.tuya.smart.sweeper.demo.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131230922;

  private View view2131230816;

  private View view2131230946;

  private View view2131230940;

  private View view2131230939;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.login_submit, "field 'mLoginSubmit' and method 'onClickLogin'");
    target.mLoginSubmit = Utils.castView(view, R.id.login_submit, "field 'mLoginSubmit'", Button.class);
    view2131230922 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickLogin();
      }
    });
    view = Utils.findRequiredView(source, R.id.country_name, "field 'mCountryName' and method 'onClickSelectCountry'");
    target.mCountryName = Utils.castView(view, R.id.country_name, "field 'mCountryName'", TextView.class);
    view2131230816 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickSelectCountry();
      }
    });
    target.mPassword = Utils.findRequiredViewAsType(source, R.id.password, "field 'mPassword'", EditText.class);
    view = Utils.findRequiredView(source, R.id.password_switch, "field 'mPasswordSwitch' and method 'onClickPasswordSwitch'");
    target.mPasswordSwitch = Utils.castView(view, R.id.password_switch, "field 'mPasswordSwitch'", ImageButton.class);
    view2131230946 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickPasswordSwitch();
      }
    });
    target.mUserName = Utils.findRequiredViewAsType(source, R.id.user_name, "field 'mUserName'", TextView.class);
    view = Utils.findRequiredView(source, R.id.option_validate_code, "method 'loginWithPhoneCode'");
    view2131230940 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.loginWithPhoneCode();
      }
    });
    view = Utils.findRequiredView(source, R.id.option_forget_password, "method 'retrievePassword'");
    view2131230939 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.retrievePassword();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLoginSubmit = null;
    target.mCountryName = null;
    target.mPassword = null;
    target.mPasswordSwitch = null;
    target.mUserName = null;

    view2131230922.setOnClickListener(null);
    view2131230922 = null;
    view2131230816.setOnClickListener(null);
    view2131230816 = null;
    view2131230946.setOnClickListener(null);
    view2131230946 = null;
    view2131230940.setOnClickListener(null);
    view2131230940 = null;
    view2131230939.setOnClickListener(null);
    view2131230939 = null;
  }
}
