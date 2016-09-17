// Generated code from Butter Knife. Do not modify!
package com.example.leejunbeom.bookMarker.ui.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class BookInfoActivity$$ViewBinder<T extends com.example.leejunbeom.bookMarker.ui.activity.BookInfoActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558500, "field 'qrcodeInfo'");
    target.qrcodeInfo = finder.castView(view, 2131558500, "field 'qrcodeInfo'");
    view = finder.findRequiredView(source, 2131558501, "field 'qrcode_scan_button' and method 'onCallBack'");
    target.qrcode_scan_button = finder.castView(view, 2131558501, "field 'qrcode_scan_button'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onCallBack();
        }
      });
  }

  @Override public void unbind(T target) {
    target.qrcodeInfo = null;
    target.qrcode_scan_button = null;
  }
}
