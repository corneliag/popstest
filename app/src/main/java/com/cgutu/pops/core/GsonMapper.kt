package com.cgutu.pops.core

import com.cgutu.pops.core.model.Order
import com.cgutu.pops.core.model.SessionToken
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


open class GsonMapper {
    val gson = gsonBuilder().create()

    /**
     *
     * @JvmSuppressWildcards suppress wildcard "? extends" in order to match with java list
     * due to List<Object> is not equal to List<? extends Object>
     *
     */
    protected open fun gsonBuilder(): GsonBuilder {
        val gsonSimple = GsonBuilder().create()
        val listTypeOrders: Type = object :
            TypeToken<List<@JvmSuppressWildcards Order>>() {}.type

        return GsonBuilder().registerTypeAdapter(SessionToken::class.java,
            object : CustomObjectDeserializer<SessionToken>() {
                override fun getSubGson(): Gson {
                    return gsonSimple
                }

                override fun getParentElement(): String {
                    return DEFAULT_PARENT_API_DATA
                }
            }).registerTypeAdapter(listTypeOrders, object : CustomListDeserializer<Order>() {
            override fun getSubGson(): Gson {
                return gsonSimple
            }

            override fun getParentElement(): String {
                return DEFAULT_PARENT_API_DATA
            }
        })

    }

    companion object {
        const val DEFAULT_PARENT_API_DATA = "data"
    }
}