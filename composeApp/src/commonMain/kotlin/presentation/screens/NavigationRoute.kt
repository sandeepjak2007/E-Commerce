package presentation.screens

sealed class NavigationRoute(val route : String) {
    data object ProductList : NavigationRoute("/product_list")
    data object ProductDetails : NavigationRoute("/product_details/{id}") {
        fun getRoute(id: Int) = "/product_details/${id}"
    }
    data object CartScreen : NavigationRoute("/cart")
}