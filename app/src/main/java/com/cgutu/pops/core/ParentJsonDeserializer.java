package com.cgutu.pops.core;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;

/**
 * Parent JsonDeserializer defines the default GsonBuilder instance which allow to manage
 * the rest of the json tree view
 *
 * @param <T> type for which the deserializer is being registered
 */
public interface ParentJsonDeserializer<T> extends JsonDeserializer<T> {
    Gson getSubGson();
}
