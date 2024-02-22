package domain.use_cases

data class SqlDelightUseCase(
    val insertProductDetailUseCase: InsertProductDetailUseCase,
    val deleteProductDetailUseCase: DeleteProductDetailUseCase,
    val getAllProductDetailUseCase: GetAllProductDetailUseCase
)
