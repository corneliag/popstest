package com.cgutu.pops

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cgutu.pops.core.model.LogOutData
import com.cgutu.pops.core.model.Order
import com.cgutu.pops.core.model.OrderStatus
import com.cgutu.pops.manager.IAuthentificationManager
import com.cgutu.pops.manager.IOrdersManager
import com.cgutu.pops.manager.OrdersManager
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class OrdersActivity : AppCompatActivity() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private lateinit var ordersManager: IOrdersManager
    private var adapter: OrdersAdapter = OrdersAdapter()
    private lateinit var authentificationManager: IAuthentificationManager

    companion object {
        private const val TAG = "OrdersActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        ordersManager = OrdersManager(PopsApplication.apiClient)
        getOrders()

        authentificationManager = PopsApplication.authentificationManager
        val logOutButton = findViewById<Button>(R.id.logout)
        logOutButton.setOnClickListener {
            logout()
        }
    }

    private fun getOrders() {
        ordersManager.getOrders(OrderStatus.DELIVERED.code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Order>> {
                override fun onSuccess(orders: List<Order>) {
                    Log.i(TAG, "data $orders")
                    val list = ArrayList<OrderItemRecyclerItem>()
                    for (order in orders) {
                        list.add(
                            OrderItem(
                                order.oderId,
                                order.email,
                                order.price,
                                "",
                                order.status
                            )
                        )
                    }
                    adapter.submitList(list)
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "Error on getting orders : $e")
                }

            })
    }

    private fun logout() {
        authentificationManager.logOut("techtesting-dev", "GFVSK-oNHB6")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<LogOutData> {
                override fun onSuccess(data: LogOutData) {
                    Log.i(TAG, "data $data")
                    if (data.status == "OK") {
                        startActivity(Intent(this@OrdersActivity, MainActivity::class.java))
                    }
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "Error on logout : $e")
                }

            })
    }
}
