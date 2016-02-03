package com.jacmobile.magicprices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jacmobile.magicprices.network.DeckBrewService;
import com.jacmobile.magicprices.network.PredictSearchDeserializer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

@Module public class AppModule
{
    private final App app;

    public AppModule(App app)
    {
        this.app = app;
    }

    @Provides @Singleton Gson provideGson()
    {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(DeckBrewService.PredictSearch.class, new PredictSearchDeserializer());
        return builder.create();
    }

    @Provides @Singleton Retrofit provideRetrofit(Gson gson)
    {
        return new Retrofit.Builder()
                .baseUrl("https://api.deckbrew.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides @Singleton DeckBrewService provideDeckBrewService(Retrofit retrofit)
    {
        return retrofit.create(DeckBrewService.class);
    }
}