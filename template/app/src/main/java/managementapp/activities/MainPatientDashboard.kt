package hos_app.main_application

import android.content.Context
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar


class MainPatientDashboard : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var drawerLayout: DrawerLayout? = null
    var toolBar: MaterialToolbar? = null
    var menu: Menu? = null
    var navigationView: NavigationView? = null
    private var toggle: ActionBarDrawerToggle? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_patient_dashboard)
        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        navigationView = findViewById<NavigationView>(R.id.navigation_drawer_view)
        toolBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        navigationView.bringToFront()
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolBar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)
        menu = navigationView.getMenu()
        menu!!.findItem(R.id.nav_login).isVisible = false
        menu!!.findItem(R.id.nav_home).isVisible = false
        navigationView.setCheckedItem(R.id.navigation_drawer_view)
        toolBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolBar)
        Objects.requireNonNull<ActionBar>(getSupportActionBar()).setDisplayHomeAsUpEnabled(true)
        getSupportActionBar().setDisplayShowHomeEnabled(true)
        val buttonAppointments: MaterialButton =
            findViewById<MaterialButton>(R.id.button_appointments)
        val buttonSummary: MaterialButton = findViewById<MaterialButton>(R.id.button_summary)
        val buttonLedger: MaterialButton = findViewById<MaterialButton>(R.id.button_ledger)
        val buttonPrescriptions: MaterialButton =
            findViewById<MaterialButton>(R.id.button_prescriptions)
        val buttonAllergies: MaterialButton = findViewById<MaterialButton>(R.id.button_allergies)


        // Set up the onClickListeners
        buttonAppointments.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainPatientDashboard, Appointments::class.java)
            startActivity(intent)
        })
        buttonSummary.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainPatientDashboard, Summary::class.java)
            startActivity(intent)
        })
        buttonLedger.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainPatientDashboard, Ledger::class.java)
            startActivity(intent)
        })
        buttonPrescriptions.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainPatientDashboard, Prescriptions::class.java)
            startActivity(intent)
        })
        buttonAllergies.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainPatientDashboard, Allergies::class.java)
            startActivity(intent)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = getMenuInflater()
        inflater.inflate(R.menu.dashboard_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        if (item.itemId == R.id.action_logout) {
            logOut()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logOut() {
        // log out logic here
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.apply()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        val itemId = menuItem.itemId
        if (itemId == R.id.nav_appointments) {
            val intent = Intent(this@MainPatientDashboard, Appointments::class.java)
            startActivity(intent)
        } else if (itemId == R.id.nav_appoint_summary) {
            val intent1 = Intent(this@MainPatientDashboard, Summary::class.java)
            startActivity(intent1)
        } else if (itemId == R.id.nav_ledger) {
            val intent2 = Intent(this@MainPatientDashboard, Ledger::class.java)
            startActivity(intent2)
        } else if (itemId == R.id.nav_prescriptions) {
            val intent4 = Intent(this@MainPatientDashboard, Prescriptions::class.java)
            startActivity(intent4)
        } else if (itemId == R.id.nav_login) {
            menu!!.findItem(R.id.nav_logout).isVisible = true
            menu!!.findItem(R.id.nav_profile).isVisible = true
            menu!!.findItem(R.id.nav_login).isVisible = false
        } else if (itemId == R.id.nav_logout) {
            menu!!.findItem(R.id.nav_logout).isVisible = false
            menu!!.findItem(R.id.nav_profile).isVisible = false
            menu!!.findItem(R.id.nav_login).isVisible = true
            logOut()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}