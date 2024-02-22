package domain.mappers

import data.model.ProductDTO
import domain.model.Product
import domain.model.ProductDetail

fun List<ProductDTO>.toDomain(): List<Product> = map {
    Product(
        id = it.id,
        image = it.image,
        title = it.title
    )
}

fun ProductDTO.toDomain(): ProductDetail {
    return ProductDetail(
        id = this.id,
        image = this.image,
        title = this.title,
        desc = this.description
    )
}