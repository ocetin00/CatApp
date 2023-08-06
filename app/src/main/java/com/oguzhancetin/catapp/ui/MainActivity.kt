package com.oguzhancetin.catapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.oguzhancetin.catapp.MainUIState
import com.oguzhancetin.catapp.MainViewModel
import com.oguzhancetin.catsapp.ui.theme.CatsAppTheme
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.transformationlayout.onTransformationStartContainer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        super.onCreate(savedInstanceState)


        setContent {
            val state by viewModel.mainUIState.collectAsState()
            CatsAppTheme {
                when (state) {
                    is MainUIState.Loading -> {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(200.dp))
                        }
                    }

                    is MainUIState.Error -> {
                        Toast.makeText(this, state.errorMessage, Toast.LENGTH_SHORT).show()
                    }

                    is MainUIState.Success -> {
                        state.errorMessage?.let {
                            Toast.makeText(this, state.errorMessage, Toast.LENGTH_SHORT).show()
                        }
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            state.catsList?.let { cats ->
                                items(cats.size, key = {
                                    cats[it].id
                                }) { index ->
                                    Column {
                                        Text(text = cats[index].url)
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            GlideImage(
                                                modifier = Modifier.size(200.dp),
                                                imageModel = { cats[index].url }, // loading a network image using an URL.
                                                imageOptions = ImageOptions(
                                                    contentScale = ContentScale.Crop,
                                                    alignment = Alignment.Center
                                                ), failure = {
                                                    Toast.makeText(
                                                        this@MainActivity,
                                                        "Failed loading image.: ${it.toString()}",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            )
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

}