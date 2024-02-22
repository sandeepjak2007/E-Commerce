package presentation.screens.cart

import data.utils.NetworkResult
import domain.use_cases.SqlDelightUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class CartViewModel(
    private val sqlDelightUseCase: SqlDelightUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CartState())

    val uiState: StateFlow<CartState> = _uiState.asStateFlow()

    init {
        getAllProducts()
    }

    fun getAllProducts() = sqlDelightUseCase.getAllProductDetailUseCase().onEach { res ->
        when (res) {
            is NetworkResult.Error -> {
                _uiState.update {
                    CartState(
                        error = it.error
                    )
                }
            }

            is NetworkResult.Loading -> {
                _uiState.update {
                    CartState(
                        isLoading = true
                    )
                }
            }

            is NetworkResult.Success -> {
                _uiState.update {
                    CartState(
                        productList = res.data
                    )
                }
            }
        }
    }.launchIn(viewModelScope)

    fun delete(id: Int) = sqlDelightUseCase.deleteProductDetailUseCase(id).onEach { res ->
            _uiState.update {
                CartState(productList = res)
            }
        }.launchIn(viewModelScope)
}