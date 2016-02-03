package com.jacmobile.magicprices;

import com.jacmobile.magicprices.view.EditionListDialog;
import com.jacmobile.magicprices.view.MainActivity;
import com.jacmobile.magicprices.view.SetsActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent
{
    void inject(MainActivity activity);
    void inject(SetsActivity activity);
    void inject(EditionListDialog fragment);
}