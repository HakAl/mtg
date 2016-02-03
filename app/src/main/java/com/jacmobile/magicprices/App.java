package com.jacmobile.magicprices;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;

public class App extends Application
{
    public static final String TAG = App.class.getSimpleName();

    private AppComponent appComponent;

    @Override public void onCreate()
    {
        super.onCreate();
        this.appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        Glide.get(this).setMemoryCategory(MemoryCategory.HIGH);
    }

    public AppComponent getAppComponent()
    {
        return appComponent;
    }
}