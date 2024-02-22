package presentation.di

import domain.use_cases.GetProductDetailsUseCase
import domain.use_cases.GetProductListUseCase
import domain.use_cases.SqlDelightUseCase
import org.koin.dsl.module
import presentation.screens.ProductListViewModel
import presentation.screens.cart.CartViewModel
import presentation.screens.product_details.ProductDetailsViewModel

val presentationModule = module {

    factory {
        ProductListViewModel(get<GetProductListUseCase>())
    }
    factory {
        ProductDetailsViewModel(get<GetProductDetailsUseCase>(), get<SqlDelightUseCase>())
    }

    factory {
        CartViewModel(get<SqlDelightUseCase>())
    }
}