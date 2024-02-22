package presentation.screens.product_details

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import domain.model.ProductDetail
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moe.tlaster.precompose.navigation.Navigator
import presentation.components.ToolBarTitle

@Composable
fun ProductDetailsScreen(
    navigator: Navigator,
    viewModel: ProductDetailsViewModel,
    onAdd: (ProductDetail) -> Unit
) {

    val uiState = viewModel.uiState.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            contentColor = Color.Black, backgroundColor = Color.White
        ) {
            ToolBarTitle(
                title = "Product Details",
            ) {
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
                    Text(
                        text = uiState.value.error
                    )
                }
            }

            else -> {
                Column(
                    modifier = Modifier.fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(12.dp)
                ) {
                    uiState.value.product?.let { productDetail ->
                        KamelImage(
                            resource = asyncPainterResource(productDetail.image),
                            contentDescription = productDetail.title,
                            modifier = Modifier.fillMaxWidth().height(250.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = productDetail.title, style = MaterialTheme.typography.h5)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = productDetail.desc, style = MaterialTheme.typography.body1)
                        Button(onClick = {
                            onAdd(productDetail)
                        }) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = "Delete item from cart",
                                    colorFilter = ColorFilter.tint(Color.White)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Add to cart")
                            }
                        }
                    }
                }
            }
        }

    }

}