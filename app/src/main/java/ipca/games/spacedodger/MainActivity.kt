package ipca.games.spacedodger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        findViewById<ImageButton>(R.id.play).setOnClickListener({
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            startActivity(intent)
        })
    }
}