package domain.repository

import domain.model.Product
import domain.model.ProductDetail

interface MainRepository {

    suspend fun getProducts(): List<Product>

    suspend fun getProductDetails(id: Int): ProductDetail

    suspend fun insert(id: Int, title: String, desc: String, image: String)

    suspend fun delete(id: Int)

    fun getProductList(): List<ProductDetail>
}