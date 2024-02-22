package data.di

import app_db.AppDatabase
import data.repository.MainRepositoryImpl
import domain.repository.MainRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.dsl.module

val dataModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json()
            }
        }
    }

    factory<MainRepository> {
        MainRepositoryImpl(get<HttpClient>(), get<AppDatabase>())
    }
}