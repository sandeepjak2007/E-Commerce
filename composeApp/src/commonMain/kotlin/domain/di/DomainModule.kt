package domain.di

import domain.use_cases.DeleteProductDetailUseCase
import domain.use_cases.GetAllProductDetailUseCase
import domain.use_cases.GetProductDetailsUseCase
import domain.use_cases.GetProductListUseCase
import domain.use_cases.InsertProductDetailUseCase
import domain.use_cases.SqlDelightUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        GetProductListUseCase()
    }

    factory {
        GetProductDetailsUseCase()
    }

    factory {
        SqlDelightUseCase(
            InsertProductDetailUseCase(),
            DeleteProductDetailUseCase(),
            GetAllProductDetailUseCase()
        )
    }
}