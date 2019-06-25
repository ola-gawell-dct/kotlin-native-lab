package sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

actual class Sample {
    actual fun checkMe() = 44
}

actual object Platform {
    actual val name: String = "Android2"
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Sample().checkMe()
        setContentView(R.layout.activity_main)

        //findViewById<TextView>(R.id.main_text).text = hello()

        val api = ApplicationApi()

        api.about {
            GlobalScope.apply {
                launch(Dispatchers.Main) {
                    findViewById<TextView>(R.id.main_text).text = it
                }
            }
        }
    }
}