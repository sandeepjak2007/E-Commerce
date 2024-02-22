package presentation.screens.cart

import domain.model.ProductDetail

data class CartState(
    val isLoading: Boolean = false,
    val productList: List<ProductDetail> = emptyList(),
    val error: String = ""
)
