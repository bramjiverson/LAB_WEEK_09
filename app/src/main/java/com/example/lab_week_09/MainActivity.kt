package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavHostController
import com.squareup.moshi.JsonAdapter
import java.net.URLEncoder
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }

        composable(
            "detail/{studentJson}",
            arguments = listOf(navArgument("studentJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("studentJson")
            val adapter: JsonAdapter<Student> = MoshiInstance.moshi.adapter(Student::class.java)
            val student = json?.let { adapter.fromJson(URLDecoder.decode(it, "UTF-8")) }
            student?.let { DetailScreen(it) }
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    val nameState = remember { mutableStateOf("") }
    val ageState = remember { mutableStateOf("") }

    val adapter: JsonAdapter<Student> = MoshiInstance.moshi.adapter(Student::class.java)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = nameState.value,
            onValueChange = { nameState.value = it },
            label = { Text("Name") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = ageState.value,
            onValueChange = { ageState.value = it },
            label = { Text("Age") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val student = Student(nameState.value, ageState.value.toIntOrNull() ?: 0)
                val json = adapter.toJson(student)
                val encoded = URLEncoder.encode(json, StandardCharsets.UTF_8.toString())
                navController.navigate("detail/$encoded")
            },
            enabled = nameState.value.isNotEmpty() && ageState.value.isNotEmpty()
        ) {
            Text("Submit")
        }
    }
}

@Composable
fun DetailScreen(student: Student) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Student Detail", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Name: ${student.name}")
        Text("Age: ${student.age}")
    }
}
