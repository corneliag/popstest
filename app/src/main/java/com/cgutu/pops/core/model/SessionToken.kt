package com.cgutu.pops.core.model

import com.google.gson.annotations.SerializedName

class SessionToken {
    @SerializedName("sessionToken")
    lateinit var token: String
}
