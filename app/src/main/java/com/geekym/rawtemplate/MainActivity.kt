package com.geekym.rawtemplate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myFragment(HomeFragment())

        val bottomNavigationView: BottomNavigationView? = findViewById(R.id.bottomNav)

        bottomNavigationView?.setOnItemSelectedListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.home -> fragment = HomeFragment()
                R.id.second -> fragment = SecondFragment()
                R.id.third -> fragment = ThirdFragment()
                R.id.profile -> fragment = ProfileFragment()
            }
            myFragment(fragment)
        }
    }

    private fun myFragment(homeFragment: Fragment?): Boolean {
        if (homeFragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.frame, homeFragment).commit()
            return true
        }
        return false
    }
}