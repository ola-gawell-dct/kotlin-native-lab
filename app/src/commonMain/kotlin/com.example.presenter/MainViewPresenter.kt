package se.grapen.multibox.kotlinnative.presenter

import com.example.api.API
import com.example.api.GetUsersResponse
import com.example.data.User
import com.example.log
import com.example.interactor.GetUsersUseCase


class MainViewPresenter(
    val api: API,
    var view: MainView? = null
) {

    fun loadUsers() {
        GetUsersUseCase(api).execute({ usersResponse: GetUsersResponse ->
            log("Users repsonse in presenter: $usersResponse")
            view?.showUsers(usersResponse.data)
        }, {
            log("Error: $it")
        }, {
            log("Cancelled: $it")
        })
    }

}

interface MainView {
    fun showUsers(users: List<User>)
}

