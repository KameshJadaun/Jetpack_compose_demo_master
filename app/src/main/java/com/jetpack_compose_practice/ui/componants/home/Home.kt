package com.jetpack_compose_practice.ui.componants.home

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.jetpack_compose_practice.model.User
import com.jetpack_compose_practice.ui.componants.home.viewmodel.HomeViewModel
import com.jetpack_compose_practice.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomePage(navController: NavController, homeViewModel: HomeViewModel) {
    val context = LocalContext.current
    init(context, homeViewModel, navController)
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxWidth(),
        topBar = { Header(scaffoldState, scope) },
        content = { Body(homeViewModel) },
        drawerContent = { DrawerView(navController, homeViewModel,scaffoldState, scope) },

        )
}

fun init(context: Context, homeViewModel: HomeViewModel, navController: NavController) {
    homeViewModel.name.value =
        navController.currentBackStackEntry?.arguments?.getString("name") ?: ""
    homeViewModel.email.value =
        navController.currentBackStackEntry?.arguments?.getString("email") ?: ""
}


@Composable
fun Header(scaffoldState: ScaffoldState, scope: CoroutineScope) {
    TopAppBar(
        title = {
            Text(text = "Home")
        },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch { scaffoldState.drawerState.open() }

            }) {
                Icon(Icons.Default.Menu, "backIcon")
            }
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = White,
        elevation = 10.dp
    )

}

@Composable
fun DrawerView(navController: NavController, homeViewModel: HomeViewModel,scaffoldState: ScaffoldState, scope: CoroutineScope) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(bottomEnd = 20.dp))
            .clickable {}) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colors.primaryVariant)
                    .padding(15.dp)

            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = "https://minimaltoolkit.com/images/randomdata/male/2.jpg"),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(
                            2.dp, color = White,
                            CircleShape
                        )
                )
                Spacer(modifier = Modifier.width(5.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Text(
                        text = homeViewModel.name.value ?: "",
                        fontSize = 20.sp,
                        color = White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = homeViewModel.email.value ?: "",
                        fontSize = 15.sp,
                        color = White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                }
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .scrollable(
                        state = rememberScrollState(0),
                        orientation = Orientation.Vertical,
                        enabled = true
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 10.dp).clickable {
                            scope.launch { scaffoldState.drawerState.close() }
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Outlined.Home, contentDescription = "")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Home", fontSize = 20.sp)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 10.dp).clickable {
                            homeViewModel.goToCms(navController)
                            scope.launch { scaffoldState.drawerState.close()}
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Outlined.Info, contentDescription = "")
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "About Us", fontSize = 20.sp)
                }

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Yellow700)
                    .align(alignment = Alignment.BottomCenter)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp, vertical = 20.dp)
                        .clickable {
                            scope.launch { scaffoldState.drawerState.close() }
                            homeViewModel.doLogout(navController)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.ExitToApp,
                        contentDescription = "",
                        modifier = Modifier.rotate(180.0f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = "Logout", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }

            }
        }

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Body(homeViewModel: HomeViewModel) {
    val pagerState = rememberPagerState()

//---------------------------------------UI-----------------------

    Column(modifier = Modifier.fillMaxSize()) {
        //-------slider, indicator------------------------
        HorizontalPager(
            count = 3, state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
        ) { page ->
            when (page) {
                0 -> homeViewModel.sliderimage.value =
                    "https://images.unsplash.com/photo-1484591974057-265bb767ef71?ixlib=rb-1.2.1&w=1080&fit=max&q=80&fm=jpg&crop=entropy&cs=tinysrgb"
                1 -> homeViewModel.sliderimage.value =
                    "https://images.unsplash.com/photo-1484591974057-265bb767ef71?ixlib=rb-1.2.1&w=1080&fit=max&q=80&fm=jpg&crop=entropy&cs=tinysrgb"
                2 -> homeViewModel.sliderimage.value =
                    "https://images.unsplash.com/photo-1484591974057-265bb767ef71?ixlib=rb-1.2.1&w=1080&fit=max&q=80&fm=jpg&crop=entropy&cs=tinysrgb"
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp, vertical = 10.dp)
            ) {
                Card(elevation = 10.dp, shape = RoundedCornerShape(20.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(model = homeViewModel.sliderimage.value),
                        contentDescription = "",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier.fillMaxSize(),
                    )
                }

            }

        }


        Box(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(vertical = 10.dp)
        ) {
            DotsIndicator(
                totalDots = 3,
                selectedIndex = pagerState.currentPage,
                selectedColor = MaterialTheme.colors.primary,
                unSelectedColor = Black
            )

        }
        //-----------end of slider, indicator--------

        //--------userList---------------------------------
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 5.dp),
        ) {
            items(homeViewModel.userList.size) { pos -> Usercard(homeViewModel.userList[pos]) }
        }
        //-----------end of userlist------------

    }

}

@Composable
fun Usercard(user: User) {
    Card(
        elevation = 10.dp, modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = user.image),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .border(
                        2.dp, color = MaterialTheme.colors.primary,
                        CircleShape
                    )
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(text = "Id: ${user.id ?: ""}", fontSize = 20.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = "Name: ${user.name ?: ""}", fontSize = 20.sp)

            }

        }

    }

}

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color,
    unSelectedColor: Color,
) {

    LazyRow(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight(), verticalAlignment = Alignment.CenterVertically

    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultHomePreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = White
    ) {
        HomePage(rememberNavController(), HomeViewModel())
    }
}