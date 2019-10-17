package com.example

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.data.User
import com.example.di.kodein
import com.example.presenter.MainView
import com.example.presenter.MainViewPresenter
import sample.R

class MainActivity : AppCompatActivity(), MainView {

    var counter = 0
    var textField: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textField = findViewById<TextView>(R.id.main_text)

        val presenter = MainViewPresenter(kodein, this@MainActivity)
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