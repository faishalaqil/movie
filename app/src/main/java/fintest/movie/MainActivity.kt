package fintest.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fintest.movie.App
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.setCurrentActivity(this)
        supportActionBar?.title = ""
    }
}