package com.shengeliia.everydayenglish.screens.launch

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.shengeliia.everydayenglish.R
import com.shengeliia.everydayenglish.screens.launch.bookmarks.BookmarksFragment
import com.shengeliia.everydayenglish.screens.launch.settings.SettingsFragment
import com.shengeliia.everydayenglish.screens.launch.tests.TestsFragment
import kotlinx.android.synthetic.main.activity_launch.*
import kotlinx.android.synthetic.main.app_bar_launch.*

class LaunchActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            if (supportFragmentManager.findFragmentById(R.id.fragmentHolder) == null) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentHolder, TestsFragment())
                    .commit()
            }
        }
        val toggle = ActionBarDrawerToggle(this, launchDrawer, toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)
        launchDrawer.addDrawerListener(toggle)
        toggle.syncState()
        launchNav.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.nav_tests -> {
                replaceFragment(TestsFragment(), TestsFragment.TAG)

            }
            R.id.nav_bookmarks -> {
                replaceFragment(BookmarksFragment(), BookmarksFragment.TAG)
            }
            R.id.nav_settings -> {
                replaceFragment(SettingsFragment(), SettingsFragment.TAG)
            }
            R.id.nav_share -> {
                Toast.makeText(this, "toast", Toast.LENGTH_SHORT).show()
            }
        }
        launchDrawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        if (fragment is TestsFragment) {
            // очистить всё, оставить только главный фрагмент
            for (i in 0 until supportFragmentManager.backStackEntryCount) {
                supportFragmentManager.popBackStackImmediate()
            }
            return
        }

        for (i in 0 until supportFragmentManager.backStackEntryCount) {
            val t = supportFragmentManager.getBackStackEntryAt(i).name!!
            if (t == tag) {
                // Если фрагмент уже был открыт, вернуться к тому состоянию стека
                supportFragmentManager.popBackStackImmediate(t, 0)
                return
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolder, fragment, tag)
            .addToBackStack(tag)
            .commit().toString()
    }

    override fun onBackPressed() {
        if (launchDrawer.isDrawerOpen(GravityCompat.START)) {
            launchDrawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    companion object {
    }

}
