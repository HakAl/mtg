package com.jacmobile.magicprices;

import com.jacmobile.magicprices.network.DeckBrewService;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import retrofit2.Retrofit;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class AppModule_ProvideDeckBrewServiceFactory implements Factory<DeckBrewService> {
  private final AppModule module;
  private final Provider<Retrofit> retrofitProvider;

  public AppModule_ProvideDeckBrewServiceFactory(AppModule module, Provider<Retrofit> retrofitProvider) {  
    assert module != null;
    this.module = module;
    assert retrofitProvider != null;
    this.retrofitProvider = retrofitProvider;
  }

  @Override
  public DeckBrewService get() {  
    DeckBrewService provided = module.provideDeckBrewService(retrofitProvider.get());
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<DeckBrewService> create(AppModule module, Provider<Retrofit> retrofitProvider) {  
    return new AppModule_ProvideDeckBrewServiceFactory(module, retrofitProvider);
  }
}

