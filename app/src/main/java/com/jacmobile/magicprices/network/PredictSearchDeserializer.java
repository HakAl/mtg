package com.jacmobile.magicprices.network;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class PredictSearchDeserializer implements JsonDeserializer
{
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonArray jsonArray = json.getAsJsonArray();

        if (jsonArray != null && jsonArray.size() > 0) {
            String[] predictions = new String[jsonArray.size()];
            for (int i = 0; i < predictions.length; i++) {
                predictions[i] =
                        JsonParseUtil.safeParseString(jsonArray.get(i).getAsJsonObject(), "name");
            }
            return new DeckBrewService.PredictSearch(new ArrayList<>(Arrays.asList(predictions)));
        } else {
            return new DeckBrewService.PredictSearch();
        }

    }
}