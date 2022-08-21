package com.jetpack_compose_practice.data.remote

import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*


/**
 * Created by Kamesh Singh.
 */

interface ApiClient {

    @POST("{apiName}")  // -- for postApi
    fun postApi(
        @Path("apiName") apiName: String,
        @Body reqJson: HashMap<String,Any?> = HashMap()
    ): Call<JsonObject>
}