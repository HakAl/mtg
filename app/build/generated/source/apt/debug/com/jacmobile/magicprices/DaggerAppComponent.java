package com.jacmobile.magicprices;

import com.google.gson.Gson;
import com.jacmobile.magicprices.network.DeckBrewService;
import com.jacmobile.magicprices.view.EditionListDialog;
import com.jacmobile.magicprices.view.MainActivity;
import com.jacmobile.magicprices.view.MainActivity_MembersInjector;
import com.jacmobile.magicprices.view.SetsActivity;
import com.jacmobile.magicprices.view.SetsActivity_MembersInjector;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.annotation.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DaggerAppComponent implements AppComponent {
  private Provider<Gson> provideGsonProvider;
  private Provider<Retrofit> provideRetrofitProvider;
  private Provider<DeckBrewService> provideDeckBrewServiceProvider;
  private MembersInjector<MainActivity> mainActivityMembersInjector;
  private MembersInjector<SetsActivity> setsActivityMembersInjector;

  private DaggerAppComponent(Builder builder) {  
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {  
    return new Builder();
  }

  private void initialize(final Builder builder) {  
    this.provideGsonProvider = ScopedProvider.create(AppModule_ProvideGsonFactory.create(builder.appModule));
    this.provideRetrofitProvider = ScopedProvider.create(AppModule_ProvideRetrofitFactory.create(builder.appModule, provideGsonProvider));
    this.provideDeckBrewServiceProvider = ScopedProvider.create(AppModule_ProvideDeckBrewServiceFactory.create(builder.appModule, provideRetrofitProvider));
    this.mainActivityMembersInjector = MainActivity_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), provideDeckBrewServiceProvider);
    this.setsActivityMembersInjector = SetsActivity_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), provideDeckBrewServiceProvider);
  }

  @Override
  public void inject(MainActivity activity) {  
    mainActivityMembersInjector.injectMembers(activity);
  }

  @Override
  public void inject(SetsActivity activity) {  
    setsActivityMembersInjector.injectMembers(activity);
  }

  @Override
  public void inject(EditionListDialog fragment) {  
    MembersInjectors.noOp().injectMembers(fragment);
  }

  public static final class Builder {
    private AppModule appModule;
  
    private Builder() {  
    }
  
    public AppComponent build() {  
      if (appModule == null) {
        throw new IllegalStateException("appModule must be set");
      }
      return new DaggerAppComponent(this);
    }
  
    public Builder appModule(AppModule appModule) {  
      if (appModule == null) {
        throw new NullPointerException("appModule");
      }
      this.appModule = appModule;
      return this;
    }
  }
}

