package com.cgutu.pops.core

import com.cgutu.pops.core.model.LogOutData
import com.cgutu.pops.core.model.Order
import com.cgutu.pops.core.model.PopsAuth
import com.cgutu.pops.core.model.SessionToken
import com.cgutu.pops.manager.IAuthentificationManager
import com.facebook.stetho.okhttp3.StethoInterceptor
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient : IApiClient {

    companion object {
        const val BASE_URL = "https://apibo3dev.pops.co"
    }

    private lateinit var api: PopsApi
    private lateinit var httpClient: OkHttpClient

    override fun initializeClient(manager: IAuthentificationManager) {
        httpClient = OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor(manager))
            .addNetworkInterceptor(StethoInterceptor())
            .build()
        configureRetrofit()
    }

    override fun configureRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonMapper().gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()

        api = retrofit.create(PopsApi::class.java)
    }

    override fun authenticate(username: String, password: String): Single<SessionToken> {
        return api.getSessionToken(PopsAuth(username, password))
    }

    class AuthenticationInterceptor(private val manager: IAuthentificationManager) : Interceptor {
        companion object {
            const val AUTHORIZATION = "sessionToken"
        }

        override fun intercept(chain: Interceptor.Chain): Response = chain.run {
            val original = chain.request()

            val session = manager.getAccessToken()
            return if (session == null) {
                chain.proceed(original.newBuilder().build())
            } else {
                val builder = original.newBuilder()
                    .header(AUTHORIZATION, session.token)

                chain.proceed(builder.build())
            }
        }
    }

    override fun getOrders(status: String): Single<List<Order>> {
        return api.getOrders(status)
    }

    override fun logout(username: String, password: String): Single<LogOutData> {
        return api.logout(PopsAuth(username, password))
    }
}