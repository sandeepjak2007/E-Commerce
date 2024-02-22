package presentation.screens

import data.utils.NetworkResult
import domain.use_cases.GetProductListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class ProductListViewModel(
    private val getProductListUseCase: GetProductListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListState())

    val uiState: StateFlow<ProductListState> = _uiState.asStateFlow()

    init {
        getProductList()
    }

    private fun getProductList() = getProductListUseCase().onEach { res ->
        when (res) {
            is NetworkResult.Loading -> {
                _uiState.update {
                    ProductListState(
                        isLoading = true
                    )
                }
            }

            is NetworkResult.Success -> {
                _uiState.update {
                    ProductListState(
                        products = res.data
                    )
                }
            }

            is NetworkResult.Error -> {
                _uiState.update {
                    ProductListState(
                        error = it.error
                    )
                }
            }
        }
    }.launchIn(viewModelScope)
}