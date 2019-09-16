package com.cgutu.pops.core;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Generic deserializer.
 */
public abstract class CustomListDeserializer<T> implements ParentJsonDeserializer<List<T>> {

    private final static String ROOT_NO_ELEMENT_DEFAULT = "ROOT_NO_ELEMENT_DEFAULT";

    @Override
    public List<T> deserialize(JsonElement json, Type type, JsonDeserializationContext jdc)
            throws JsonParseException {

        if (json.isJsonObject()) {
            if (!ROOT_NO_ELEMENT_DEFAULT.equals(getParentElement())) {
                return getSubGson().fromJson(json.getAsJsonObject().get(getParentElement()), type);
            }
            Set<Map.Entry<String, JsonElement>> entries = json.getAsJsonObject().entrySet();
            if (entries == null || entries.isEmpty()) {
                return new ArrayList<>();
            }
            if (entries.size() > 1) {
                throw new RuntimeException("Json must have only one element");
            }
            return getSubGson().fromJson(entries.iterator().next().getValue(), type);
        } else {
            return getSubGson().fromJson(json, type);
        }
    }

    /**
     * We need to set the root String to get the array of the root associated
     *
     * @return Root element of json object
     */
    public String getParentElement() {
        return ROOT_NO_ELEMENT_DEFAULT;
    }
}
