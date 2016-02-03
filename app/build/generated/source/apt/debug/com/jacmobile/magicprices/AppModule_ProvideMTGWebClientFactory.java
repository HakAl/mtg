package com.jacmobile.magicprices;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class AppModule_ProvideMTGWebClientFactory implements Factory<MTGWebClient> {
  private final AppModule module;

  public AppModule_ProvideMTGWebClientFactory(AppModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public MTGWebClient get() {  
    MTGWebClient provided = module.provideMTGWebClient();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<MTGWebClient> create(AppModule module) {  
    return new AppModule_ProvideMTGWebClientFactory(module);
  }
}

