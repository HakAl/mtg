package com.jacmobile.magicprices;

import com.google.gson.Gson;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class AppModule_ProvideRetrofitFactory implements Factory<Retrofit> {
  private final AppModule module;
  private final Provider<Gson> gsonProvider;

  public AppModule_ProvideRetrofitFactory(AppModule module, Provider<Gson> gsonProvider) {  
    assert module != null;
    this.module = module;
    assert gsonProvider != null;
    this.gsonProvider = gsonProvider;
  }

  @Override
  public Retrofit get() {  
    Retrofit provided = module.provideRetrofit(gsonProvider.get());
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<Retrofit> create(AppModule module, Provider<Gson> gsonProvider) {  
    return new AppModule_ProvideRetrofitFactory(module, gsonProvider);
  }
}

