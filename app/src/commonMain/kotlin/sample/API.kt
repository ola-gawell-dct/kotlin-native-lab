package sample

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import kotlinx.serialization.json.Json
import kotlinx.serialization.Serializable

interface API {
    suspend fun getUsers(): GetUsersResponse
}

class KtorAPI: API {

    private val baseUrl: String = "https://reqres.in/api/"

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json.nonstrict).apply {
                setMapper(GetUsersResponse::class, GetUsersResponse.serializer())
            }
        }
    }

    private fun buildUrl(path: String): String {
        return "${baseUrl}${path}"
    }

    override suspend fun getUsers(): GetUsersResponse {
        return client.get(buildUrl("users"))
    }
}

@Serializable
data class User(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String
)

@Serializable
data class GetUsersResponse(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<User>
)
