package com.jacmobile.magicprices;

import com.google.gson.Gson;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class AppModule_ProvideGsonFactory implements Factory<Gson> {
  private final AppModule module;

  public AppModule_ProvideGsonFactory(AppModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public Gson get() {  
    Gson provided = module.provideGson();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<Gson> create(AppModule module) {  
    return new AppModule_ProvideGsonFactory(module);
  }
}

