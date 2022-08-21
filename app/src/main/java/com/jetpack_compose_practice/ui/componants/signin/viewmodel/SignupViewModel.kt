package com.jetpack_compose_practice.ui.componants.signin.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.jetpack_compose_practice.data.remote.Repository
import com.jetpack_compose_practice.model.resmodel.UserInfoRes
import com.jetpack_compose_practice.utils.*

class SignupViewModel() : ViewModel() {
    private val repository by lazy { Repository() }
    val name = mutableStateOf("")
    val email = mutableStateOf("")
    val pass = mutableStateOf("")
    val confirmPass = mutableStateOf("")
    val isNameError = mutableStateOf(false)
    val nameErrorText = mutableStateOf("")
    val isEmailError = mutableStateOf(false)
    val emailErrorText = mutableStateOf("")
    val isPassError = mutableStateOf(false)
    val passErrorText = mutableStateOf("")
    val isConfirmPassError = mutableStateOf(false)
    val confirmPassErrorText = mutableStateOf("")
    val showDialog = mutableStateOf(false)
    val showLoader = mutableStateOf(false)


    private fun callApiSignup(context: Context, navController: NavHostController) {
        val reqData = HashMap<String, Any?>()
        reqData["name"] = name.value ?: ""
        reqData["email"] = email.value ?: ""
        reqData["password"] = pass.value ?: ""
        repository.callPostApi(Config.register, reqData)?.observe(context as LifecycleOwner, Observer {
            showLoader.value = false
            if (it != null) {
                val model = convertToModel(it) ?: UserInfoRes()
                navController.navigate("$pageHome?name=${model.data?.name ?: ""},email=${model.data?.email ?: ""}") {
                    popUpTo(pageSignup) { inclusive = true }
                }
            }
        })

    }


    fun validateForm(context: Context, navController: NavHostController) {
        if (name.value.trim().isEmpty()) {
            isNameError.value = true
            nameErrorText.value = "Please enter your name"
        } else if (email.value.trim().isEmpty()) {
            isNameError.value = false
            nameErrorText.value = ""
            isEmailError.value = true
            emailErrorText.value = "Please enter email address"
        } else if (!Utility.isEmailValid(email.value)) {
            isNameError.value = false
            nameErrorText.value = ""
            isEmailError.value = true
            emailErrorText.value = "Please enter valid email address"
        } else if (pass.value.trim().isEmpty()) {
            isNameError.value = false
            nameErrorText.value = ""
            isEmailError.value = false
            emailErrorText.value = ""
            isPassError.value = true
            passErrorText.value = "Please enter your password"
        } else if (confirmPass.value.trim().isEmpty()) {
            isNameError.value = false
            nameErrorText.value = ""
            isEmailError.value = false
            emailErrorText.value = ""
            isPassError.value = false
            passErrorText.value = ""
            isConfirmPassError.value = true
            confirmPassErrorText.value = "Please re-enter your password"
        } else if (confirmPass.value.compareTo(pass.value) != 0) {
            isNameError.value = false
            nameErrorText.value = ""
            isEmailError.value = false
            emailErrorText.value = ""
            isPassError.value = false
            passErrorText.value = ""
            isConfirmPassError.value = true
            confirmPassErrorText.value = "Password mismatched. please enter correct password"
        } else {
            isNameError.value = false
            nameErrorText.value = ""
            isEmailError.value = false
            emailErrorText.value = ""
            isPassError.value = false
            passErrorText.value = ""
            isConfirmPassError.value = false
            confirmPassErrorText.value = ""
            if (Utility.isOnline(context)) {
                showLoader.value = true
                callApiSignup(context, navController)
            }
        }
    }

    fun validateName(value: String?) {
        if (value == null || value.trim().isEmpty()) {
            isNameError.value = true
            nameErrorText.value = "Please enter your name"
        } else {
            isNameError.value = false
            nameErrorText.value = ""
        }
    }

    fun validateEmail(value: String?) {
        if (value == null || value.trim().isEmpty()) {
            isEmailError.value = true
            emailErrorText.value = "Please enter email address"
        } else if (!Utility.isEmailValid(value)) {
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

    fun validateConfirmPass(value: String?) {
        if (value == null || value.trim().isEmpty()) {
            isConfirmPassError.value = true
            confirmPassErrorText.value = "Please re-enter your password"
        } else if (value.compareTo(pass.value) != 0) {
            isConfirmPassError.value = true
            confirmPassErrorText.value = "Password mismatched. please enter correct password"
        } else {
            isConfirmPassError.value = false
            confirmPassErrorText.value = ""
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