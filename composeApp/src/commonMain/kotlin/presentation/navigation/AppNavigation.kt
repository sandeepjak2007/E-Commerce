package presentation.navigation

import androidx.compose.runtime.Composable
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.rememberNavigator
import presentation.screens.NavigationRoute
import presentation.screens.ProductListScreen
import presentation.screens.ProductListViewModel
import presentation.screens.cart.CartScreen
import presentation.screens.cart.CartViewModel
import presentation.screens.product_details.ProductDetailsScreen
import presentation.screens.product_details.ProductDetailsViewModel

@Composable
fun AppNavigation() {
    val navigator = rememberNavigator()

    NavHost(
        navigator = navigator, initialRoute = NavigationRoute.ProductList.route
    ) {
        scene(route = NavigationRoute.ProductList.route) {
            val viewModel: ProductListViewModel = koinViewModel(ProductListViewModel::class)
            ProductListScreen(viewModel, navigator) {
                navigator.navigate(NavigationRoute.ProductDetails.getRoute(id = it))
            }
        }

        scene(route = NavigationRoute.ProductDetails.route) { bEntry ->
            val id = bEntry.path.filter { it.isDigit() }
            val viewModel: ProductDetailsViewModel = koinViewModel(ProductDetailsViewModel::class)
            viewModel.getProductDetail(id.toInt())
            ProductDetailsScreen(navigator, viewModel) {
                viewModel.insert(it.id, it.title, it.image, it.desc)
            }
        }

        scene(route = NavigationRoute.CartScreen.route) {
            val viewModel: CartViewModel = koinViewModel(CartViewModel::class)
            CartScreen(viewModel, navigator) {
                viewModel.delete(it)
            }
        }
    }

}