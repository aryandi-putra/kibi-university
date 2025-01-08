package com.aryandi.university.feature.home

import android.content.Intent
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aryandi.university.domain.model.DataResult
import com.aryandi.university.domain.model.University
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
            MainContent()
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun MainContent() {
        var expanded by rememberSaveable { mutableStateOf(false) }
        val searchText by viewModel.searchText.collectAsState()
        UniversityTheme {
            Scaffold(
                topBar = {
                    SearchBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .semantics { traversalIndex = 0f },
                        inputField = {
                            SearchBarDefaults.InputField(
                                query = searchText,
                                onQueryChange = { viewModel.onSearchTextChange(it) },
                                onSearch = { expanded = false },
                                expanded = expanded,
                                onExpandedChange = { expanded = it },
                                placeholder = { Text("Cari Universitas") },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Search,
                                        contentDescription = null
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        Icons.Default.MoreVert,
                                        contentDescription = null
                                    )
                                },
                            )
                        },
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        content = {}
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .padding(start = 4.dp, end = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val dataResult by viewModel.universities.collectAsState()
                    ListContent(dataResult)
                }
            }
        }
    }
}

@Composable
private fun ListContent(dataResult: DataResult<List<University>>) {
    when (dataResult) {
        is DataResult.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp),
                color = Color.Blue
            )
        }

        is DataResult.Error -> {
            Toast.makeText(
                LocalContext.current,
                dataResult.error,
                Toast.LENGTH_SHORT
            ).show()
        }

        is DataResult.Success -> {
            val universities = dataResult.data ?: emptyList()
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items = universities) {
                    UniversityItem(university = it)
                }
            }
        }
    }
}

@Composable
@Preview(
    name = "University List",
    showBackground = true
)
fun UniversityListPreview() {
    ListContent(
        DataResult.Success(
            listOf(
                University(
                    "Institut Teknologi Bandung",
                    "Indonesia",
                    "www.itb.ac.id"
                ),
                University(
                    "Universitas Indonesia",
                    "Indonesia",
                    "www.ui.ac.id"
                ),
                University(
                    "Universitas Lampung",
                    "Indonesia",
                    "www.unila.ac.id"
                ),
            )
        )
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
                    putExtra("webPage", university.webPage)
                })
            },
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(text = university.name)
        Text(text = university.country)
        Text(text = university.webPage)
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
@Preview(
    name = "University Item",
    showBackground = true
)
fun UniversityItemPreview() {
    UniversityItem(University("Universitas Lampung", "Indonesia", "www.unila.ac.id"))
}