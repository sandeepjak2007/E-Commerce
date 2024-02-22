package presentation.screens.product_details

import data.utils.NetworkResult
import domain.use_cases.GetProductDetailsUseCase
import domain.use_cases.SqlDelightUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class ProductDetailsViewModel(
    private val productDetailsUseCase: GetProductDetailsUseCase,
    private val sqlDelightUseCase: SqlDelightUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailsState())

    val uiState: StateFlow<ProductDetailsState> = _uiState.asStateFlow()

    fun getProductDetail(id: Int) = productDetailsUseCase(id).onEach { res ->

        when (res) {
            is NetworkResult.Loading -> {
                _uiState.update {
                    ProductDetailsState(
                        isLoading = true
                    )
                }
            }

            is NetworkResult.Success -> {
                _uiState.update {
                    ProductDetailsState(
                        product = res.data
                    )
                }
            }

            is NetworkResult.Error -> {
                ProductDetailsState(
                    error = res.message
                )
            }
        }
    }.launchIn(viewModelScope)

    fun insert(id: Int, title: String, image: String, desc: String) =
        sqlDelightUseCase.insertProductDetailUseCase(id, title, image, desc)
            .launchIn(viewModelScope)
}