package fintest.movie

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import fintest.movie.App


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        App.setCurrentActivity(this)

        Handler().postDelayed({
        val intentNoLogin = Intent(this, MainActivity::class.java)
        startActivity(intentNoLogin)
        finish()
        }, 2000)
    }
}
