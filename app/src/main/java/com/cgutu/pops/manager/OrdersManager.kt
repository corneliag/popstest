package com.cgutu.pops.manager

import com.cgutu.pops.core.IApiClient
import com.cgutu.pops.core.model.Order
import io.reactivex.Single

class OrdersManager(private val apiClient: IApiClient) : IOrdersManager {
    override fun getOrders(status: String): Single<List<Order>> {
        return apiClient.getOrders(status)
    }
}