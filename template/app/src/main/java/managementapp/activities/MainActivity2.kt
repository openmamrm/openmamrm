package hos_app.main_application

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

// TODO: Merge the two MainActivities together

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if the user is logged in or not
        val sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        val role = sharedPreferences.getString("role", "")

// Start the appropriate activity based on the login status and role
        val intent: Intent
        if (isLoggedIn) {
            intent = if ("patient" == role) {
                Intent(this, MainPatientDashboard::class.java)
            } else {
                Intent(this, DoctorDashboard::class.java) // Use your doctor dashboard activity here
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        } else {
            intent = Intent(this, LoginActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}