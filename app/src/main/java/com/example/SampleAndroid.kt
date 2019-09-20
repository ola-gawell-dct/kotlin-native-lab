package com.example

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.data.User
import org.kodein.di.erased.instance
import org.kodein.di.newInstance
import sample.R
import se.grapen.multibox.kotlinnative.di.KodeinProxy
import se.grapen.multibox.kotlinnative.di.kodein
import se.grapen.multibox.kotlinnative.presenter.MainView
import se.grapen.multibox.kotlinnative.presenter.MainViewPresenter

class MainActivity : AppCompatActivity(), MainView {

    var counter = 0
    var textField: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textField = findViewById<TextView>(R.id.main_text)

        val presenter by KodeinProxy().getKodein().newInstance { MainViewPresenter(instance(), this@MainActivity) }
        presenter.loadUsers()

        findViewById<Button>(R.id.main_button).setOnClickListener {
            textField?.text = "Counter: $counter"
            counter++
        }
    }

    override fun showUsers(users: List<User>) {
        textField?.text = "Result ${users[0].first_name}"
    }
}