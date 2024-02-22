package presentation.screens.product_details

import domain.model.Product
import domain.model.ProductDetail

data class ProductDetailsState(
    val isLoading: Boolean = false,
    val product: ProductDetail? = null,
    val error: String = ""
)
