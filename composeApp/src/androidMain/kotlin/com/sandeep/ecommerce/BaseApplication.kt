package com.sandeep.ecommerce

import android.app.Application
import initKoin
import org.koin.dsl.module

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            it.modules(module {
                single {
                    this@BaseApplication.applicationContext
                }
            })
        }
    }
}