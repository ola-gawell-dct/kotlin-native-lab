package sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

actual class Sample {
    actual fun checkMe() = 44
}

actual object Platform {
    actual val name: String = "Android2"
}

actual fun log(msg: String) {
    Log.d("Custom Log", msg)
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Sample().checkMe()
        setContentView(R.layout.activity_main)

        //findViewById<TextView>(R.id.main_text).text = hello()


        val vm = SamplePresenter()
        vm.view = object: SampleView {
            override fun setLabel(text: String) {
                findViewById<TextView>(R.id.main_text).text = text
            }
        }
        vm.getData()


        /*val api = ApplicationApi()

        api.about {
            GlobalScope.apply {
                launch(Dispatchers.Main) {
                    findViewById<TextView>(R.id.main_text).text = it
                }
            }
        }*/
    }
}