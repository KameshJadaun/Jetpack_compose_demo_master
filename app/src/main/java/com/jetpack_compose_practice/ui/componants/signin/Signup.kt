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
import com.jetpack_compose_practice.ui.customviews.OutlineTextFieldWithErrorView
import com.jetpack_compose_practice.ui.componants.signin.viewmodel.SignupViewModel
import com.jetpack_compose_practice.ui.theme.*


@Composable
fun Signup(signupViewModel: SignupViewModel, navController: NavHostController) {
    val context = LocalContext.current


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
            textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(), text = "SignUp",
            fontSize = 30.sp, fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(40.dp))
/*--Name--*/
        OutlineTextFieldWithErrorView(value = signupViewModel.name.value, modifier = Modifier
            .fillMaxWidth()
            .background(color = White),
            onValueChange = {
                Log.d("namevaluechange", "$it")
                signupViewModel.name.value = it
                signupViewModel.validateName(it)
            },
            textStyle = TextStyle(fontSize = 18.sp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = {
                Text(
                    text = "Name",
                    color = MaterialTheme.colors.primary,
                    fontSize = 18.sp
                )
            },
            isError = signupViewModel.isNameError.value,
            errorMsg = if(signupViewModel.isNameError.value)signupViewModel.nameErrorText.value else ""

        )
        Spacer(Modifier.height(20.dp))
/*--Email--*/
        OutlineTextFieldWithErrorView(value = signupViewModel.email.value, modifier = Modifier
            .fillMaxWidth()
            .background(color = White),
            onValueChange = {
                Log.d("emailvaluechange", "$it")
                signupViewModel.email.value = it
                signupViewModel.validateEmail(it)
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
            isError = signupViewModel.isEmailError.value,
            errorMsg = if(signupViewModel.isEmailError.value)signupViewModel.emailErrorText.value else ""
        )
        Spacer(Modifier.height(20.dp))
/*--password--*/
        OutlineTextFieldWithErrorView(value = signupViewModel.pass.value, modifier = Modifier
            .fillMaxWidth()
            .background(color = White),
            onValueChange = {
                Log.d("passvaluechange", "$it")
                signupViewModel.pass.value = it
                signupViewModel.validatePass(it)
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
            isError = signupViewModel.isPassError.value,
            errorMsg = if(signupViewModel.isPassError.value)signupViewModel.passErrorText.value else ""

        )

        Spacer(Modifier.height(20.dp))
/*--confirm password--*/
        OutlineTextFieldWithErrorView(value = signupViewModel.confirmPass.value, modifier = Modifier
            .fillMaxWidth()
            .background(color = White),
            onValueChange = {
                Log.d("passvaluechange", "$it")
                signupViewModel.confirmPass.value = it
                signupViewModel.validateConfirmPass(it)
            },
            textStyle = TextStyle(fontSize = 18.sp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = {
                Text(
                    text = "Confirm Password",
                    color = MaterialTheme.colors.primary,
                    fontSize = 18.sp
                )
            },
            isError = signupViewModel.isConfirmPassError.value,
            errorMsg = if(signupViewModel.isConfirmPassError.value)signupViewModel.confirmPassErrorText.value else ""
        )

        Spacer(Modifier.height(60.dp))
        Button(
            onClick = {
                      signupViewModel.validateForm(context,navController)
            },
            Modifier
                .height(45.dp)
                .fillMaxWidth(),
        ) {
            Text(text = "Signup", color = White, fontSize = 18.sp)
        }
        Spacer(Modifier.height(40.dp))

        OutlinedButton(
            onClick = {
                navController.popBackStack()
            },
            Modifier
                .height(45.dp)
                .fillMaxWidth(),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary)
        ) {
            Text(
                text = "Already registered? Login",
                color = MaterialTheme.colors.primary,
                fontSize = 18.sp
            )
        }

    }


}

@Preview(showBackground = true)
@Composable
fun DefaultSignupPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = White
    ) {
        Signup(SignupViewModel(), rememberNavController())
    }
}

