package com.shengeliia.everydayenglish.screens.launch

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.shengeliia.data.local.LocalDatabase
import com.shengeliia.data.local.PreferencesManager
import com.shengeliia.data.local.PreferencesManager.checkUserIsLogin
import com.shengeliia.everydayenglish.R
import com.shengeliia.everydayenglish.screens.intro.IntroActivity
import com.shengeliia.everydayenglish.screens.launch.bookmarks.BookmarksFragment
import com.shengeliia.everydayenglish.screens.launch.settings.SettingsFragment
import com.shengeliia.everydayenglish.screens.launch.tests.TestsFragment

class LaunchActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var launchToolbar: Toolbar
    private lateinit var launchDrawer: DrawerLayout
    private lateinit var launchNavigation: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!checkUserIsLogin(this)) {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
        }

        setContentView(R.layout.activity_launch)
        findViews()
        setSupportActionBar(launchToolbar)
        setNavigationHeaderData()

        if (supportFragmentManager.findFragmentById(R.id.launchFragmentHolder) == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.launchFragmentHolder, TestsFragment())
                .commit()
        }

        if (savedInstanceState == null) {
            LocalDatabase.init(this.applicationContext)
        }

        val toggle = ActionBarDrawerToggle(this, launchDrawer, launchToolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)
        launchDrawer.addDrawerListener(toggle)
        toggle.syncState()
        launchNavigation.setNavigationItemSelectedListener(this)
    }

    private fun setNavigationHeaderData() {
        val header = launchNavigation.getHeaderView(0)
        val iconView: ImageView = header.findViewById(R.id.launchNavHeaderIcon)
        val nameView: TextView = header.findViewById(R.id.launchNavHeaderName)

        val name = PreferencesManager.getUsername(this)
        nameView.text = name
        
        val level = PreferencesManager.getLevel(this)
        if (level == PreferencesManager.LEVEL_BEGINNER) {
            iconView.setImageResource(R.drawable.ic_beginner)
        } else if (level == PreferencesManager.LEVEL_ADVANCED) {
            iconView.setImageResource(R.drawable.ic_advanced)
        }
    }

    private fun findViews() {
        launchToolbar = findViewById(R.id.launchToolbar)
        launchDrawer = findViewById(R.id.launchDrawer)
        launchNavigation = findViewById(R.id.launchNavigation)
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
            // очистить стек, оставить только главный фрагмент
            for (i in 0 until supportFragmentManager.backStackEntryCount) {
                supportFragmentManager.popBackStackImmediate()
            }
            return
        }

        // Если фрагмент уже есть в стеке, вернуться к нему, очистив всё, что выше
        for (i in 0 until supportFragmentManager.backStackEntryCount) {
            val t = supportFragmentManager.getBackStackEntryAt(i).name!!
            if (t == tag) {
                supportFragmentManager.popBackStackImmediate(t, 0)
                return
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.launchFragmentHolder, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

    override fun onBackPressed() {
        if (launchDrawer.isDrawerOpen(GravityCompat.START)) {
            launchDrawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    companion object

}
