package hos_app.main_application

import android.content.Context
import android.view.View
import android.widget.Button
import com.google.firebase.database.DataSnapshot
import java.util.*

class LoginActivity : AppCompatActivity() {
    // code to be executed when button is clicked
    var button: Button? = null
    private var passField: TextInputEditText? = null
    private var nameField: TextInputEditText? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_main)
        nameField = findViewById<TextInputEditText>(R.id.get_name)
        passField = findViewById<TextInputEditText>(R.id.get_password)
        button = findViewById<Button>(R.id.submit_login)
        button!!.setOnClickListener { v: View? ->
            val username2: String = Objects.requireNonNull<Editable>(nameField.getText()).toString()
            val password2: String = Objects.requireNonNull<Editable>(passField.getText()).toString()
            if (!username2.isEmpty() && !password2.isEmpty()) {
                openNewActivity(username2, password2)
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    "Please Enter the Username or Password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    fun openNewActivity(username: String?, password: String) {
        val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")
        val currentUserRef: DatabaseReference = usersRef.child(username)
        currentUserRef.addListenerForSingleValueEvent(object : ValueEventListener() {
            fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val storedPassword: String = dataSnapshot.child("password").getValue(
                        String::class.java
                    )
                    val role: String = dataSnapshot.child("role").getValue(String::class.java)
                    val practitionerName: String = dataSnapshot.getKey()
                    if (storedPassword != null && storedPassword == password) {
                        val intent: Intent
                        val sharedPreferences: SharedPreferences =
                            getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putBoolean("isLoggedIn", true)
                        editor.putString("role", role)
                        editor.putString("practitionerName", practitionerName)
                        editor.apply()
                        if ("patient" == role) {
                            intent = Intent(this@LoginActivity, MainPatientDashboard::class.java)
                        } else {
                            intent = Intent(this@LoginActivity, DoctorDashboard::class.java)
                        }
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Wrong Password", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "User not found", Toast.LENGTH_SHORT).show()
                }
            }

            fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    this@LoginActivity,
                    "Error: " + databaseError.getMessage(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}