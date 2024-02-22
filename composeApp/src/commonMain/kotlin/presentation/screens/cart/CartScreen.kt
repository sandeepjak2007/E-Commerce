package presentation.screens.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moe.tlaster.precompose.navigation.Navigator
import presentation.components.ToolBarTitle

@Composable
fun CartScreen(
    viewModel: CartViewModel,
    navigator: Navigator,
    onDelete: (Int) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            contentColor = Color.Black, backgroundColor = Color.White
        ) {
            ToolBarTitle("Cart") {
                navigator.goBack()
            }
        }
    }) {

        when {
            uiState.value.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.value.error.isNotEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(uiState.value.error)
                }
            }

            else -> {
                if (uiState.value.productList.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Text("Cart is empty.!!")
                    }
                } else {
                    LazyColumn {
                        items(uiState.value.productList) { productDetail ->
                            Column(
                                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(12.dp)
                            ) {
                                KamelImage(
                                    resource = asyncPainterResource(productDetail.image),
                                    contentDescription = productDetail.title,
                                    modifier = Modifier.fillMaxWidth().height(250.dp)
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = productDetail.title, style = MaterialTheme.typography.h5
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = productDetail.desc,
                                    style = MaterialTheme.typography.body1
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Button(onClick = {
                                    onDelete(productDetail.id)
                                }) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ){
                                        Image(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete item from cart",
                                            colorFilter = ColorFilter.tint(Color.White)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(text = "Delete")
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