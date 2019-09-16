package com.cgutu.pops.core

import com.cgutu.pops.core.model.LogOutData
import com.cgutu.pops.core.model.Order
import com.cgutu.pops.core.model.SessionToken
import com.cgutu.pops.manager.IAuthentificationManager
import io.reactivex.Single

interface IApiClient {

    fun initializeClient(manager: IAuthentificationManager)

    fun configureRetrofit()

    fun authenticate(username: String, password: String): Single<SessionToken>

    fun getOrders(status: String): Single<List<Order>>

    fun logout(username: String, password: String): Single<LogOutData>
}