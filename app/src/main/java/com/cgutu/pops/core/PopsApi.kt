package com.cgutu.pops.core

import com.cgutu.pops.core.model.LogOutData
import com.cgutu.pops.core.model.Order
import com.cgutu.pops.core.model.PopsAuth
import com.cgutu.pops.core.model.SessionToken
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PopsApi {

    @POST("/login")
    fun getSessionToken(@Body auth: PopsAuth): Single<SessionToken>

    @POST("logout")
    fun logout(@Body auth: PopsAuth): Single<LogOutData>

    @GET("orders")
    fun getOrders(@Query("orderStatus") status: String): Single<List<Order>>
}