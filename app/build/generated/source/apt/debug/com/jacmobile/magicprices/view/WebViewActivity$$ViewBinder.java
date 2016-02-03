// Generated code from Butter Knife. Do not modify!
package com.jacmobile.magicprices.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class WebViewActivity$$ViewBinder<T extends com.jacmobile.magicprices.view.WebViewActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492977, "field 'webView'");
    target.webView = finder.castView(view, 2131492977, "field 'webView'");
    view = finder.findRequiredView(source, 2131492978, "field 'progressBar'");
    target.progressBar = finder.castView(view, 2131492978, "field 'progressBar'");
  }

  @Override public void unbind(T target) {
    target.webView = null;
    target.progressBar = null;
  }
}
