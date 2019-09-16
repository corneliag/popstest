package com.cgutu.pops.manager

import com.cgutu.pops.core.model.LogOutData
import com.cgutu.pops.core.model.SessionToken
import io.reactivex.Single

interface IAuthentificationManager {
    fun authenticate(username: String, password: String): Single<SessionToken>

    fun getAccessToken(): SessionToken?

    fun setToken(token: SessionToken?)

    fun logOut(username: String, password: String):Single<LogOutData>
}