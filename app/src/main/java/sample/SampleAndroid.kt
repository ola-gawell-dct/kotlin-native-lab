package sample

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textField = findViewById<TextView>(R.id.main_text)

        val api = KtorAPI()
        val useCase = GetUsersUseCase(api)
        useCase.execute({ result: GetUsersResponse ->
            log("Result: $result")
            textField.text = "Result ${result.data[0].first_name}"
        }, {
            log("Error: $it")
            textField.text = "Error: $it"
        }, {
            log("Cancelled: $it")
            textField.text = "Cancelled: $it"
        })

        findViewById<Button>(R.id.main_button).setOnClickListener {
            textField.text = "Counter: $counter"
            counter++
        }
    }
}