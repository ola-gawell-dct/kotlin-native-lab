package com.example.interactor

import com.example.api.API
import com.example.api.GetUsersResponse
import com.example.MainDispatcher
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class GetUsersUseCase(
    private val api: API
) {

    val context: CoroutineContext = MainDispatcher

    private var job: Job = Job()

    fun execute(
        onComplete: (GetUsersResponse) -> Unit,
        onError: ((Throwable) -> Unit)? = null,
        onCancel: ((CancellationException) -> Unit)? = null
    ) {
        job = Job()
        CoroutineScope(context + job).launch {
            try {
                val result = api.getUsers()
                onComplete(result)
            } catch (cancellationException: CancellationException) {
                onCancel?.invoke(cancellationException)
            } catch (e: Exception) {
                onError?.invoke(e)
            }
        }
    }
}