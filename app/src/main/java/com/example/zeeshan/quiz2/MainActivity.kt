package com.example.zeeshan.quiz2

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.zeeshan.quiz2.fragments.*
import com.example.zeeshan.quiz2.interfaces.FragmentSignupInteraction
import com.example.zeeshan.quiz2.interfaces.FragmentUsersInteraction
import com.example.zeeshan.quiz2.models.User
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener , AboutFragment.OnFragmentInteractionListener, FragmentSignupInteraction, ProfileFragment.OnFragmentInteractionListener {

    lateinit var aboutFragment : AboutFragment
    lateinit var signinFragemnt : SignInFragment
    lateinit var signupFragment : SignUpFragment
    lateinit var allUsersFragment: AllUserFragment
    lateinit var profileFragment: ProfileFragment

    var recieveDataInteraction : FragmentUsersInteraction? = null


    override fun sendData(user: User) {
//        Toast.makeText(this, "Current User:\n${user.toString()}", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "Successfully Added a User", Toast.LENGTH_SHORT).show()

        recieveDataInteraction?.recieveData(user)
    }

    public fun setRecieveInteraction(recieveInteraction: FragmentUsersInteraction){
        this.recieveDataInteraction = recieveInteraction
    }

//    public fun showPopup(){
//        val dialogBuilder = AlertDialog.Builder(this@MainActivity)
//        val create: AlertDialog = dialogBuilder.create()
//
//        dialogBuilder.setCancelable(false)
//
//        dialogBuilder.setTitle("User Added!")
//        dialogBuilder.setMessage("User Added Successfully.")
//
//        dialogBuilder.setPositiveButton("OK", object : DialogInterface.OnClickListener{
//            override fun onClick(dialog: DialogInterface?, which: Int) {
//
//            }
//        })
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)



        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        aboutFragment = AboutFragment()
        signinFragemnt = SignInFragment()
        signupFragment = SignUpFragment()
        allUsersFragment = AllUserFragment()
        profileFragment = ProfileFragment()
        
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.right_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         //Handle action bar item clicks here. The action bar will
         //automatically handle clicks on the Home/Up button, so long
         //as you specify a parent activity in AndroidManifest.xml.
//        return super.onOptionsItemSelected(item)
        when (item.itemId) {
            R.id.nav_signIn -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_main, signinFragemnt)
                    .addToBackStack(signinFragemnt.toString())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
                return true
            }
            R.id.nav_signUp -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_main, signupFragment)
                    .addToBackStack(signupFragment.toString())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
                return true
            }
            R.id.nav_about -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_main, aboutFragment)
                    .addToBackStack(aboutFragment.toString())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
                return true
            }

            else -> return false
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_main, aboutFragment)
                    .addToBackStack(aboutFragment.toString())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.nav_profile -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_main, profileFragment)
                    .addToBackStack(profileFragment.toString())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.nav_users ->{
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container_main, allUsersFragment)
                    .addToBackStack(allUsersFragment.toString())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.nav_share -> {
                val shareIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(shareIntent, "Feel free to share this app with your friends."))
//               var sharingIntent : Intent = Intent().apply {
//                   action = Intent.ACTION_SEND
//               }

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

        override fun onFragmentInteraction(uri: Uri) {
            Log.d("Fragment","On Fragment Interaction")
        }
    }
