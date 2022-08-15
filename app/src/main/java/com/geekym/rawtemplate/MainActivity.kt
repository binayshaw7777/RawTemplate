package com.geekym.rawtemplate

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var bottomNavigationView: BottomNavigationView? = null
        myFragment(HomeFragment())

        bottomNavigationView = findViewById(R.id.bottomNav)
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.home -> fragment = HomeFragment()
                R.id.second -> fragment = SecondFragment()
                R.id.third -> fragment = ThirdFragment()
                R.id.profile -> fragment = ProfileFragment()
            }
            myFragment(fragment)
        })

    }
    private fun myFragment(homeFragment: Fragment?): Boolean {
        if (homeFragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.frame, homeFragment).commit()
            return true
        }
        return false
    }
}