package com.jacmobile.magicprices.view;

import android.support.v7.app.AppCompatActivity;
import com.jacmobile.magicprices.network.DeckBrewService;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class SetsActivity_MembersInjector implements MembersInjector<SetsActivity> {
  private final MembersInjector<AppCompatActivity> supertypeInjector;
  private final Provider<DeckBrewService> deckBrewServiceProvider;

  public SetsActivity_MembersInjector(MembersInjector<AppCompatActivity> supertypeInjector, Provider<DeckBrewService> deckBrewServiceProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert deckBrewServiceProvider != null;
    this.deckBrewServiceProvider = deckBrewServiceProvider;
  }

  @Override
  public void injectMembers(SetsActivity instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.deckBrewService = deckBrewServiceProvider.get();
  }

  public static MembersInjector<SetsActivity> create(MembersInjector<AppCompatActivity> supertypeInjector, Provider<DeckBrewService> deckBrewServiceProvider) {  
      return new SetsActivity_MembersInjector(supertypeInjector, deckBrewServiceProvider);
  }
}

