package com.cgutu.pops.manager

import com.cgutu.pops.core.IApiClient
import com.cgutu.pops.core.model.LogOutData
import com.cgutu.pops.core.model.SessionToken
import io.reactivex.Single

class AuthentificationManager(private val apiClient: IApiClient) : IAuthentificationManager {

    private var token: SessionToken? = null

    override fun authenticate(username: String, password: String): Single<SessionToken> {
        return apiClient.authenticate(username, password)
    }

    override fun getAccessToken(): SessionToken? {
        return token
    }

    override fun setToken(token: SessionToken?) {
        this.token = token
    }

    override fun logOut(username: String, password: String): Single<LogOutData> {
        return apiClient.logout(username, password)
    }
}