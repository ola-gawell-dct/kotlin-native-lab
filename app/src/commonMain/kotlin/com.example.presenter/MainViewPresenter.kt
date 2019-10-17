package com.example.presenter

import com.example.api.API
import com.example.api.GetUsersResponse
import com.example.data.User
import com.example.log
import com.example.interactor.GetUsersUseCase
import org.kodein.di.Kodein
import org.kodein.di.erased.instance


class MainViewPresenter(
    kodein: Kodein,
    var view: MainView? = null
) {

    private val api: API by kodein.instance()

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

