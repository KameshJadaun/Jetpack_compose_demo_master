package com.jetpack_compose_practice.ui.componants.home.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.jetpack_compose_practice.model.User
import com.jetpack_compose_practice.utils.pageCMS
import com.jetpack_compose_practice.utils.pageHome
import com.jetpack_compose_practice.utils.pageLogin
import com.jetpack_compose_practice.utils.pageSignup

class HomeViewModel : ViewModel() {
    val sliderimage =
        mutableStateOf("https://images.unsplash.com/photo-1484591974057-265bb767ef71?ixlib=rb-1.2.1&w=1080&fit=max&q=80&fm=jpg&crop=entropy&cs=tinysrgb")
    val name = mutableStateOf("")
    val email = mutableStateOf("")

    val userList =
        mutableStateListOf(
            User(
                id = "A23V394",
                name = "Jack anderson",
                image = "https://minimaltoolkit.com/images/randomdata/male/2.jpg"
            )
        )

    init {
        userList.clear()
        userList.addAll(createList())
    }

    private fun createList(): MutableList<User> {
        val list = mutableListOf<User>()
        val usr1 = User(
            id = "A23V394",
            name = "Jack Anderson",
            image = "https://minimaltoolkit.com/images/randomdata/male/2.jpg"
        )
        list.add(usr1)

        val usr2 = User(
            id = "A23V395",
            name = "Robert James",
            image = "https://minimaltoolkit.com/images/randomdata/male/2.jpg"
        )
        list.add(usr2)

        val usr3 = User(
            id = "A23V396",
            name = "Kate watson",
            image = "https://minimaltoolkit.com/images/randomdata/male/2.jpg"
        )
        list.add(usr3)

        val usr4 = User(
            id = "A23V397",
            name = "Ana willson",
            image = "https://minimaltoolkit.com/images/randomdata/male/2.jpg"
        )
        list.add(usr4)
        return list
    }

    fun doLogout(navController:NavController){
        navController.popBackStack()
        navController.navigate(pageLogin)
    }
    fun goToCms(navController: NavController){
        navController.navigate(pageCMS)
    }
}