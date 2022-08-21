package com.jetpack_compose_practice.ui.componants.signin.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.jetpack_compose_practice.data.remote.Repository
import com.jetpack_compose_practice.model.resmodel.UserInfoRes
import com.jetpack_compose_practice.utils.Config
import com.jetpack_compose_practice.utils.Utility.isEmailValid
import com.jetpack_compose_practice.utils.Utility.isOnline
import com.jetpack_compose_practice.utils.pageHome
import com.jetpack_compose_practice.utils.pageLogin

class LoginViewModel : ViewModel() {
    private val repository by lazy { Repository() }
    val email = mutableStateOf("")
    val pass = mutableStateOf("")
    val isEmailError = mutableStateOf(false)
    val emailErrorText = mutableStateOf("")
    val isPassError = mutableStateOf(false)
    val passErrorText = mutableStateOf("")
    val showDialog = mutableStateOf(false)
    val dialogMessage = mutableStateOf(false)
    val showLoader = mutableStateOf(false)


    private fun callApiLogin(context: Context, navController: NavHostController) {
        val reqData = HashMap<String, Any?>()
        reqData["email"] = email.value ?: ""
        reqData["password"] = pass.value ?: ""
        repository.callPostApi(Config.login, reqData)?.observe(context as LifecycleOwner, Observer {
            showLoader.value = false
            if (it != null) {
                val model = convertToModel(it) ?: UserInfoRes()
                navController.navigate("$pageHome?name=${model.data?.name ?: ""},email=${model.data?.email ?: ""}") {
                    popUpTo(pageLogin) { inclusive = true }
                }
            }
        })

    }


    fun validateForm(context: Context, navController: NavHostController) {
        if (email.value.trim().isEmpty()) {
            isEmailError.value = true
            emailErrorText.value = "Please enter email address"
        } else if (!isEmailValid(email.value)) {
            isEmailError.value = true
            emailErrorText.value = "Please enter valid email address"
        } else if (pass.value.trim().isEmpty()) {
            isEmailError.value = false
            emailErrorText.value = ""
            isPassError.value = true
            passErrorText.value = "Please enter your password"
        } else {
            isEmailError.value = false
            emailErrorText.value = ""
            isPassError.value = false
            passErrorText.value = ""
            if (isOnline(context)) {
                showLoader.value = true
                callApiLogin(context, navController)
            }
        }
    }

    fun validateEmail(value: String?) {
        if (value == null || value.trim().isEmpty()) {
            isEmailError.value = true
            emailErrorText.value = "Please enter email address"
        } else if (!isEmailValid(value)) {
            isEmailError.value = true
            emailErrorText.value = "Please enter valid email address"
        } else {
            isEmailError.value = false
            emailErrorText.value = ""
        }
    }

    fun validatePass(value: String?) {
        if (value == null || value.trim().isEmpty()) {
            isPassError.value = true
            passErrorText.value = "Please enter your password"
        } else {
            isPassError.value = false
            passErrorText.value = ""
        }
    }

    private fun convertToModel(json: JsonObject): UserInfoRes? {
        val gson = Gson()
        var userInfoRes: UserInfoRes? = null
        val type = object : TypeToken<UserInfoRes>() {}.type
        userInfoRes = try {
            gson.fromJson(json, type)
        } catch (ex: Exception) {
            ex.printStackTrace()
            UserInfoRes()
        }
        return userInfoRes
    }

}