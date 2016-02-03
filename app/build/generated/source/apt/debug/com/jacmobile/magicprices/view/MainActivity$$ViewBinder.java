// Generated code from Butter Knife. Do not modify!
package com.jacmobile.magicprices.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.jacmobile.magicprices.view.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492975, "field 'multiStateView'");
    target.multiStateView = finder.castView(view, 2131492975, "field 'multiStateView'");
    view = finder.findRequiredView(source, 2131493007, "field 'webView'");
    target.webView = finder.castView(view, 2131493007, "field 'webView'");
    view = finder.findRequiredView(source, 2131492976, "field 'listMain'");
    target.listMain = finder.castView(view, 2131492976, "field 'listMain'");
    view = finder.findRequiredView(source, 2131492992, "field 'progressBar'");
    target.progressBar = finder.castView(view, 2131492992, "field 'progressBar'");
    view = finder.findRequiredView(source, 2131492974, "field 'searchCards'");
    target.searchCards = finder.castView(view, 2131492974, "field 'searchCards'");
    view = finder.findRequiredView(source, 2131492988, "field 'emptyView'");
    target.emptyView = finder.castView(view, 2131492988, "field 'emptyView'");
  }

  @Override public void unbind(T target) {
    target.multiStateView = null;
    target.webView = null;
    target.listMain = null;
    target.progressBar = null;
    target.searchCards = null;
    target.emptyView = null;
  }
}
