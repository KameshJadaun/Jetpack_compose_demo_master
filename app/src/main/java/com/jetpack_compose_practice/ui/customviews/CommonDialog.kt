package com.jetpack_compose_practice.ui.customviews

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.jetpack_compose_practice.ui.theme.Yellow700

@Composable
fun CustomDialog(message: String, setShowDialog: (Boolean) -> Unit) {

    val txtFieldError = remember { mutableStateOf("") }
    val messageValue = remember { mutableStateOf(message) }

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "",
                        tint = Yellow700,
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp).align(alignment = Alignment.End)
                            .clickable { setShowDialog(false) }
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                        text = messageValue.value?:"",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = FontFamily.Serif,
                        )
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                        Button(
                            onClick = {
                                setShowDialog(false)
                            },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Text(text = "Ok")
                        }
                }
            }
        }
    }


}
@Preview
@Composable
fun showDlgPreview(){
    val showDialog = remember {
        mutableStateOf(false)
    }
    CustomDialog(message = "hello world", setShowDialog = {showDialog.value=it})
}