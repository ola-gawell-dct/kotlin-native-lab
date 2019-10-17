package com.example.di

import com.example.api.API
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider
import org.kodein.di.erased.singleton

val kodein = Kodein {
    bind<kotlin.String>("baseUrl") with provider { "https://reqres.in/api/" }
    bind<API>() with singleton {
        val baseUrl by kodein.instance<kotlin.String>("baseUrl")
        com.example.api.KtorAPI(baseUrl)
    }
}

class KodeinDI {
    fun getKodein(): Kodein {
        return kodein
    }
}