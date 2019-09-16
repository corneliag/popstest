package com.cgutu.pops

import android.app.Application
import com.cgutu.pops.core.ApiClient
import com.cgutu.pops.core.IApiClient
import com.cgutu.pops.manager.AuthentificationManager
import com.cgutu.pops.manager.IAuthentificationManager
import com.facebook.stetho.Stetho

class PopsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)

        apiClient = ApiClient()
        authentificationManager = AuthentificationManager(apiClient)
        apiClient.initializeClient(authentificationManager)
    }

    companion object {
        lateinit var apiClient: IApiClient
        lateinit var authentificationManager: IAuthentificationManager
    }

}