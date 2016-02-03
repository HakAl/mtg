// Generated code from Butter Knife. Do not modify!
package com.jacmobile.magicprices.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SetsActivity$$ViewBinder<T extends com.jacmobile.magicprices.view.SetsActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492976, "field 'listMain'");
    target.listMain = finder.castView(view, 2131492976, "field 'listMain'");
    view = finder.findRequiredView(source, 2131492992, "field 'progressBar'");
    target.progressBar = finder.castView(view, 2131492992, "field 'progressBar'");
  }

  @Override public void unbind(T target) {
    target.listMain = null;
    target.progressBar = null;
  }
}
