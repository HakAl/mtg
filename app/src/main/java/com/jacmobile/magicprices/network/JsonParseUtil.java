package com.jacmobile.magicprices.network;

import android.support.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Helper utility to avoid null checks for custom GSON deserializers.
 */
public class JsonParseUtil
{
    public static final String ERROR = "e";
    public static final String HREF = "href";
    public static final String URL = "url";
    public static final String EMBEDDED = "_embedded";
    public static final String ITEMS = "items";

    public static int safeParseInt(JsonObject object, String key)
    {
        if (object.has(key) && !object.get(key).isJsonNull()) {
            return object.get(key).getAsInt();
        }
        return 0;
    }

    public static String safeParseString(JsonObject object, String key)
    {
        if (object.has(key) && !object.get(key).isJsonNull()) {
            return object.get(key).getAsString();
        }
        return ERROR;
    }

    public static String safeParseHref(JsonObject object, String key)
    {
        if (object.has(key) && !object.get(key).isJsonNull()) {
            JsonObject o = safeParseJsonObject(object, key);
            if (o != null) {
                return safeParseString(o, HREF);
            }
        }
        return ERROR;
    }

    public static String safeParseUrl(JsonObject object, String key)
    {
        if (object.has(key) && !object.get(key).isJsonNull()) {
            JsonObject o = safeParseJsonObject(object, key);
            if (o != null) {
                return safeParseString(o, URL);
            }
        }
        return ERROR;
    }

    @Nullable public static JsonObject safeParseJsonObject(JsonObject object, String key)
    {
        if (object.has(key) && !object.get(key).isJsonNull()) {
            return object.get(key).getAsJsonObject();
        }
        return null;
    }

    @Nullable public static JsonArray safeParseJsonArray(JsonObject object, String key)
    {
        if (object.has(key) && !object.get(key).isJsonNull()) {
            return object.get(key).getAsJsonArray();
        }
        return null;
    }

    @Nullable public static JsonArray safeParseEmbeddedItems(JsonObject object)
    {
        JsonObject embedded = safeParseJsonObject(object, EMBEDDED);
        if (embedded != null) {
            return safeParseJsonArray(embedded, ITEMS);
        }
        return null;
    }
}