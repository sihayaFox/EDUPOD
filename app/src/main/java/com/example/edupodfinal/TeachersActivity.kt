package com.example.edupodfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class TeachersActivity : AppCompatActivity()  {

    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navigationView: NavigationView
    private lateinit var listener:NavController.OnDestinationChangedListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        drawerLayout = findViewById(R.id.drawer_layout)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        navigationView = findViewById(R.id.nav_view)

        navigationView.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

//        navigationView.setNavigationItemSelectedListener(this)

        navigationView.menu!!.findItem(R.id.nav_logout).setOnMenuItemClickListener {
            logout()
            true
        }

    }

    override fun onSupportNavigateUp(): Boolean {

//        val navController = findNavController(R.id.nav_host_fragment)

        return NavigationUI.navigateUp(navController, drawerLayout)
    }

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//
//        when(item.itemId){
//
//            R.id.nav_logout ->{
//                FirebaseAuth.getInstance().signOut().also {
//                    startActivity(Intent(this, LoginActivity::class.java)).also {
//                        finish()
//                    }
//                }
//            }
//        }
//
//        return true
//    }

    fun logout(){
        FirebaseAuth.getInstance().signOut().also {
            startActivity(Intent(this, LoginActivity::class.java)).also {
                finish()
            }
        }

    }




}