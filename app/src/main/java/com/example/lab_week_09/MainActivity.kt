package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme
import com.example.lab_week_09.ui.theme.OnBackgroundItemText
import com.example.lab_week_09.ui.theme.OnBackgroundTitleText
import com.example.lab_week_09.ui.theme.PrimaryTextButton

data class InputField(var name: String = "")
data class Item(val name: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {

    var inputField by remember { mutableStateOf(InputField()) }
    var listData by remember { mutableStateOf(listOf<Item>()) }

    Surface(modifier = Modifier.fillMaxSize()) {

        LazyColumn {
            item {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Title text
                    OnBackgroundTitleText(text = stringResource(id = R.string.enter_item))

                    // TextField WITHOUT keyboardOptions
                    TextField(
                        value = inputField.name,
                        onValueChange = { inputField = InputField(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                    // Submit button
                    PrimaryTextButton(
                        text = stringResource(id = R.string.button_click)
                    ) {
                        if (inputField.name.isNotBlank()) {
                            listData = listData + Item(inputField.name)
                            inputField = InputField("")
                        }
                    }
                }
            }

            items(listData) { item ->
                Column(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OnBackgroundItemText(text = item.name)
                }
            }
        }
    }
}
