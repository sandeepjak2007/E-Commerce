package data.repository

import app_db.AppDatabase
import data.model.ProductDTO
import domain.mappers.toDomain
import domain.model.Product
import domain.model.ProductDetail
import domain.repository.MainRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MainRepositoryImpl(
    private val httpClient: HttpClient, private val appDatabase: AppDatabase
) : MainRepository {
    override suspend fun getProducts(): List<Product> {
        return httpClient.get("https://fakestoreapi.com/products/")
            .body<List<ProductDTO>>()
            .toDomain()
    }

    override suspend fun getProductDetails(id: Int): ProductDetail {
        return httpClient.get("https://fakestoreapi.com/products/$id")
            .body<ProductDTO>().toDomain()
    }

    override suspend fun insert(id: Int, title: String, desc: String, image: String) {
        appDatabase.appDatabaseQueries.insert(id.toLong(), image, title, desc)
    }

    override suspend fun delete(id: Int) {
        appDatabase.appDatabaseQueries.delete(id.toLong())
    }

    override fun getProductList(): List<ProductDetail> {
        return appDatabase.appDatabaseQueries.getProductList().executeAsList().map {
            ProductDetail(
                id = it.id.toInt(),
                title = it.title.toString(),
                desc = it.desc.toString(),
                image = it.image.toString()
            )
        }
    }
}