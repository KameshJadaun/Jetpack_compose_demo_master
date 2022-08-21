package com.jetpack_compose_practice.ui.componants.signin

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jetpack_compose_practice.ui.customviews.CustomDialog
import com.jetpack_compose_practice.ui.customviews.CustomLoader
import com.jetpack_compose_practice.ui.customviews.OutlineTextFieldWithErrorView
import com.jetpack_compose_practice.ui.componants.signin.viewmodel.LoginViewModel
import com.jetpack_compose_practice.ui.theme.*
import com.jetpack_compose_practice.utils.pageSignup

@Composable
fun Login(loginViewModel: LoginViewModel, navController: NavHostController) {
    val context = LocalContext.current

    if (loginViewModel.showDialog.value) {
        CustomDialog(message = "go to singup page ", setShowDialog = { loginViewModel.showDialog.value = it })
    }
    if (loginViewModel.showLoader.value) {
        CustomLoader(setShowLoading = { loginViewModel.showDialog.value = it })
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = White
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .scrollable(
                    state = rememberScrollState(0),
                    orientation = Orientation.Vertical,
                    enabled = true
                )
                .padding(horizontal = 20.dp), verticalArrangement = Arrangement.Center
        ) {
            Text(
                textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(), text = "Login",
                fontSize = 30.sp, fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(40.dp))
/*--Email--*/
            OutlineTextFieldWithErrorView(
                value = loginViewModel.email.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = White),
                onValueChange = {
                    Log.d("emailvaluechange", "$it")
                    loginViewModel.email.value = it
                    loginViewModel.validateEmail(it)
                },
                textStyle = TextStyle(fontSize = 18.sp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                label = {
                    Text(
                        text = "Email",
                        color = MaterialTheme.colors.primary,
                        fontSize = 18.sp
                    )
                },
                isError = loginViewModel.isEmailError.value,
                errorMsg = if(loginViewModel.isEmailError.value)loginViewModel.emailErrorText.value else ""
            )
            Spacer(Modifier.height(40.dp))
/*--Pass--*/
            OutlineTextFieldWithErrorView(
                value = loginViewModel?.pass?.value ?: "", modifier = Modifier
                    .fillMaxWidth()
                    .background(color = White),
                onValueChange = {
                    Log.d("passvaluechange", "$it")
                    loginViewModel.pass.value = it
                    loginViewModel.validatePass(it)
                },
                textStyle = TextStyle(fontSize = 18.sp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                label = {
                    Text(
                        text = "Password",
                        color = MaterialTheme.colors.primary,
                        fontSize = 18.sp
                    )
                },
                isError = loginViewModel.isPassError.value,
                errorMsg = if(loginViewModel.isPassError.value)loginViewModel.passErrorText.value else ""
            )
            Spacer(Modifier.height(60.dp))
/*--Login button--*/
            Button(
                onClick = {
                    /* navController.navigate(pageHome){
                         popUpTo(pageLogin){inclusive=true}
                     }*/
                    loginViewModel.validateForm(context,navController)
                },
                Modifier
                    .height(45.dp)
                    .fillMaxWidth(),
            ) {
                Text(text = "Login", color = White, fontSize = 18.sp)
            }
            Spacer(Modifier.height(40.dp))
/*--Signup button--*/
            OutlinedButton(
                onClick = {
                     navController.navigate(pageSignup)
                    //showDialog.value = true
                },
                Modifier
                    .height(45.dp)
                    .fillMaxWidth(),
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary)
            ) {
                Text(
                    text = "New user? Sign Up",
                    color = MaterialTheme.colors.primary,
                    fontSize = 18.sp
                )
            }

        }
    }


}

@Preview(showBackground = true)
@Composable
fun DefaultLoginPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = White
    ) {
        Login(LoginViewModel(), rememberNavController())
    }
}