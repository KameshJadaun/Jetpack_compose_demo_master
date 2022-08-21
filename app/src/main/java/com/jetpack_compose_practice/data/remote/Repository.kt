package com.jetpack_compose_practice.data.remote

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository() {
    private var apiClient: ApiClient = ServiceGenerator.getInstanse().clientService

    //----------------Post method with params----------------------------

    fun callPostApi(
        api: String,
        reqData: HashMap<String,Any?> = HashMap()
    ): MutableLiveData<JsonObject?>? {

          //  if (showLoading) Utility.showLoader(context)
         //   if (hideKeyboard) Utility.hideKeyboard(context as Activity)
            val jsnRes = MutableLiveData<JsonObject?>()
            apiClient.postApi(api,reqData)
                .enqueue(object : Callback<JsonObject> {
                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>
                    ) {
                        jsnRes.value = validateResponse(response)
                        Log.d("response___api", "____________________${jsnRes.value}")
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        jsnRes.value = null
                        //Utility.showErrorDialog(context, "Error", context.getString(R.string.request_failed))
                       // Utility.hideLoder()
                    }
                })
            return jsnRes

    }




    //----------------Post method with params----------------------------

    //--------CommonMethod----------------------------
    fun validateResponse(
        response: Response<JsonObject>
    ): JsonObject? {
        val jsnRes = MutableLiveData<JsonObject?>()
        if (response.isSuccessful) {
            if (response.body() != null) {
                if ((response.body()!!.get("code") != null && response.body()!!.get("code").asInt == 0 && response.body()!!.get("data") != null)
                ) {
                    jsnRes.value = response.body()
                } else {
                   /* Utility.showErrorDialog(
                        context,
                        "${response.body()!!.get("status")}",
                        "${response.body()!!.get("message")}"
                    )*/
                    jsnRes.value = null
                }
            } else {
                /*Utility.showErrorDialog(
                    context,
                    "Error",
                    context.getString(R.string.something_went_wrong)
                )*/

                jsnRes.value = null
            }
        } else {
            /*Utility.showErrorResponse(
                context,
                response.errorBody(),
                response.raw()
            )*/
            jsnRes.value = null
        }
       // Utility.hideLoder()
        return jsnRes.value
    }


}