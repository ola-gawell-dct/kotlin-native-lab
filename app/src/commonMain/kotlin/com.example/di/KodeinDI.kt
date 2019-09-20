package se.grapen.multibox.kotlinnative.di

import com.example.api.API
import com.example.api.KtorAPI
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.provider
import org.kodein.di.erased.singleton

internal val kodein = Kodein {
        bind<String>("baseUrl") with provider { "https://reqres.in/api/" }
        bind<API>() with singleton {
            val baseUrl by kodein.instance<String>("baseUrl")
            KtorAPI(baseUrl)
        }
    }


class KodeinProxy {
    internal fun getKodein(): Kodein {
        return kodein
    }
}

/*
class SimpleDI {
    private val baseUrl = "https://us-central1-demoword-fe207.cloudfunctions.net/api/"
    val api: MultiboxApi = KtorMultiboxApi(baseUrl)
}


val kodein = SimpleDI()

 */