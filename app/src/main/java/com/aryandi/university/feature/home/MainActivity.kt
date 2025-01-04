package com.aryandi.university.feature.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.aryandi.university.data.model.ApiResult
import com.aryandi.university.data.model.University
import com.aryandi.university.feature.detail.UniversityWebActivity
import com.aryandi.university.ui.theme.UniversityTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UniversityTheme {
                Scaffold(
                    topBar = { CustomAppBar() },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .padding(start = 4.dp, end = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        val apiResult by viewModel.universities.collectAsState()
                        when (apiResult) {
                            is ApiResult.Loading -> {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(50.dp),
                                    color = Color.Blue
                                )
                            }

                            is ApiResult.Error -> {
                                Toast.makeText(
                                    this@MainActivity,
                                    apiResult.error,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            is ApiResult.Success -> {
                                val universities = apiResult.data ?: emptyList()
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    items(items = universities) { quote ->
                                        UniversityItem(university = quote)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text("Indonesian University")
        }
    )
}

@Composable
fun UniversityItem(university: University) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                context.startActivity(Intent(context, UniversityWebActivity::class.java).apply {
                    putExtra("webPage", university.webPages[0])
                })
            },
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(text = university.name)
        Text(text = university.country)
        Text(text = university.webPages[0])
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}