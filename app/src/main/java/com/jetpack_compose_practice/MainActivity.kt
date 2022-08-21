package com.jetpack_compose_practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jetpack_compose_practice.ui.componants.home.CmsPage
import com.jetpack_compose_practice.ui.componants.home.HomePage
import com.jetpack_compose_practice.ui.componants.home.viewmodel.HomeViewModel
import com.jetpack_compose_practice.ui.componants.signin.Login
import com.jetpack_compose_practice.ui.componants.signin.Signup
import com.jetpack_compose_practice.ui.componants.signin.viewmodel.LoginViewModel
import com.jetpack_compose_practice.ui.componants.signin.viewmodel.SignupViewModel
import com.jetpack_compose_practice.ui.theme.Jetpack_compose_practiceTheme
import com.jetpack_compose_practice.utils.*

class MainActivity : ComponentActivity() {
    private var loginViewModel: LoginViewModel? = null
    private var signupViewModel: SignupViewModel? = null
    private var homeViewModel: HomeViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewModel = LoginViewModel()
        signupViewModel = SignupViewModel()
        homeViewModel = HomeViewModel()
        setContent {
            Jetpack_compose_practiceTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = pageLogin) {
                    composable(route = pageLogin) {
                        Login(loginViewModel!!, navController)
                    }
                    composable(route = pageSignup) {
                        Signup(signupViewModel!!, navController)
                    }
                    composable(route = "$pageHome?name={name},email={email}", arguments = listOf(navArgument(name = "name") {
                        type = NavType.StringType
                    }, navArgument(name = "email") {
                        type = NavType.StringType
                    })) {
                        HomePage(navController, homeViewModel!!)
                    }
                    composable(route = pageCMS) {
                        CmsPage(navController)
                    }
                }

            }
        }
    }
}












