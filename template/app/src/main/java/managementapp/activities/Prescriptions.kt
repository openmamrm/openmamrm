package hos_app.main_application

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import java.util.*

class Prescriptions : AppCompatActivity() {
    var toolBar: MaterialToolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescriptions)
        toolBar = findViewById(R.id.topAppBar)
        setSupportActionBar(toolBar)
        Objects.requireNonNull(supportActionBar).setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.dashboard_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            val intent = Intent(this@Prescriptions, MainPatientDashboard::class.java)
            startActivity(intent)
            finish()
            return true
        } else if (id == R.id.action_logout) {
            logOut()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logOut() {
        // log out logic here
        val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.apply()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        setResult(RESULT_OK)
        finish()
    }
}