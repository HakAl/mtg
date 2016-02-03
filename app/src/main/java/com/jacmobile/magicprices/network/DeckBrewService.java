package com.jacmobile.magicprices.network;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DeckBrewService
{
    /**
     * Network interface methods.
     */
    @GET("/mtg/sets") Call<List<MTGSet>> listSets();

    @GET("/mtg/cards?") Call<List<MTGCard>> searchCards(@Query("name") String query);

    @GET("/mtg/cards/typeahead?") Call<PredictSearch> predictSearch(@Query("q") String partQuery);

    /**
     * Model
     */
    class MTGCard
    {
        public final String name;
        public final String id;
        public final String url;
        public final String store_url;
        public final String[] types;
        public final String[] colors;
        public final String cmc;// converted mana cost
        public final String cost;//casting cost
        public final String text;
        public final Edition[] editions;

        public MTGCard(String name, String id, String url, String store_url, String[] types, String[] colors, String cmc, String cost, String text, Edition[] editions)
        {
            this.name = name;
            this.id = id;
            this.url = url;
            this.store_url = store_url;
            this.types = types;
            this.colors = colors;
            this.cmc = cmc;
            this.cost = cost;
            this.text = text;
            this.editions = editions;
        }
    }

    class Edition
    {
        public final String set;
        public final String set_id;
        public final String rarity;
        public final String artist;
        public final String multiverse_id;
        public final String flavor;
        public final String number;
        public final String layout;
//        public final Price price;
        public final String url;
        public final String image_url;
        public final String set_url;
        public final String store_url;

        public Edition(String set, String set_id, String rarity, String artist, String multiverse_id, String flavor, String number, String layout, String url, String image_url, String set_url, String store_url)
        {
            this.set = set;
            this.set_id = set_id;
            this.rarity = rarity;
            this.artist = artist;
            this.multiverse_id = multiverse_id;
            this.flavor = flavor;
            this.number = number;
            this.layout = layout;
            this.url = url;
            this.image_url = image_url;
            this.set_url = set_url;
            this.store_url = store_url;
        }
    }

    //currently all zero
    class Price {
        public final BigDecimal low;
        public final BigDecimal median;
        public final BigDecimal high;

        public Price(BigDecimal low, BigDecimal median, BigDecimal high)
        {
            this.low = low;
            this.median = median;
            this.high = high;
        }
    }

    class MTGSet
    {
        public final String id;
        public final String name;
        public final String border;
        public final String type;
        public final String url;
        public final String cards_url;

        public MTGSet(String id, String name, String border, String type, String url, String cards_url)
        {
            this.id = id;
            this.name = name;
            this.border = border;
            this.type = type;
            this.url = url;
            this.cards_url = cards_url;
        }
    }

    class PredictSearch
    {
        public final ArrayList<String> results;

        public PredictSearch()
        {
            this.results = new ArrayList<>();
        }

        public PredictSearch(ArrayList<String> results)
        {
            this.results = results;
        }
    }
}