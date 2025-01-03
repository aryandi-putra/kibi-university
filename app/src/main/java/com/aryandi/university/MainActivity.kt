package com.aryandi.university

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aryandi.university.data.model.ApiResult
import com.aryandi.university.data.model.University
import com.aryandi.university.ui.theme.UniversityTheme
import com.aryandi.university.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val apiResult by viewModel.universities.collectAsState()
            when (apiResult) {
                is ApiResult.Loading -> {
                }

                is ApiResult.Error -> {
                }

                is ApiResult.Success -> {
                }
            }
            UniversityTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(start = 4.dp, end = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
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

@Composable
fun UniversityItem(university: University) {
    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement =Arrangement.spacedBy(4.dp) ) {
        Text(text = university.name)
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}