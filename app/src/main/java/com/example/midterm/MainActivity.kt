package com.example.midterm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.midterm.model.User
import com.example.midterm.ui.theme.MidtermTheme

class MainActivity : ComponentActivity() {
    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getUsers()
        setContent {
            MidtermTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val userResult = viewModel.users.observeAsState(Result.loading()).value
                    Column {
                        topBar()
                        UserList(userResult)
                    }
                }
            }
        }
    }
}

@Composable
fun UserList(userResult: Result<List<User>>) {
    when (userResult) {
        is Result.Success -> {
            val users = userResult.data
            LazyColumn {
                items(users) { user ->
                    Card(
                        Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp)),
                        backgroundColor = Color.LightGray
                    ) {
                        Column(
                            Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = user.name.first + " " + user.name.last,
                                style = MaterialTheme.typography.h5,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = user.email,
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                }
            }
        }
        is Result.Error -> {
            Text(text = "Error: ${userResult.exception.message}")
        }
        else -> {

        }
    }
}

@Composable
fun topBar() {
    TopAppBar(title = {
        Text(
            text = "Users",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    })
}

