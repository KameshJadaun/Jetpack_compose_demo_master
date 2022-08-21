package com.jetpack_compose_practice.ui.customviews

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jetpack_compose_practice.R
import com.jetpack_compose_practice.ui.theme.Yellow700

@Composable
fun CustomLoader(setShowLoading: (Boolean) -> Unit) {
    Dialog(onDismissRequest = { setShowLoading(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.Transparent
        ) {
            Box(modifier = Modifier
                .size(100.dp)
                .padding(all = 10.dp),
                contentAlignment = Alignment.Center
            ) {

                Image(
                    painterResource(id = R.drawable.ic_logo),
                    contentDescription = "",

                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .width(75.dp)
                        .height(75.dp).clip(shape = CircleShape)
                )
                CircularProgressIndicator(
                    modifier = Modifier.size(100.dp),
                    color = Yellow700,
                    strokeWidth = 10.dp
                )

            }
        }
    }
}

@Preview
@Composable
fun showLoaderPreview() {
    val showLoader = remember {
        mutableStateOf(false)
    }
    CustomLoader(setShowLoading = { showLoader.value = it })
}