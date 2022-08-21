package com.jetpack_compose_practice.model.resmodel


import com.google.gson.annotations.SerializedName

data class UserInfoRes(
    @SerializedName("code")
    var code: Int?=null,
    @SerializedName("data")
    var `data`: Data?=null,
    @SerializedName("message")
    var message: String?=null
) {
    data class Data(
        @SerializedName("Email")
        var email: String?=null,
        @SerializedName("Id")
        var id: Int?=null,
        @SerializedName("Name")
        var name: String?=null,
        @SerializedName("Token")
        var token: String?=null
    )
}