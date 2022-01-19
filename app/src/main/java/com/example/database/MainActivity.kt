package com.example.database

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.isVisible
import com.example.database.files.ContainerFilesFragment
import com.example.database.room.RoomFragment
import com.example.database.sharedprefs.PreferencesFragment
import com.example.database.sqlite.SQLiteDatabaseFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonsContainer = findViewById<LinearLayout>(R.id.buttons_container)

        val buttonClickListener = View.OnClickListener {
            val fragment = when (it.id) {
                R.id.button_prefs -> PreferencesFragment()
                R.id.button_sqlite -> SQLiteDatabaseFragment()
                R.id.button_room -> RoomFragment()
                R.id.button_files -> ContainerFilesFragment()
                else -> return@OnClickListener
            }

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()
        }

        buttonsContainer
            .children
            .forEach {
                it.setOnClickListener(buttonClickListener)
            }

        supportFragmentManager
            .addOnBackStackChangedListener {
                buttonsContainer.isVisible = supportFragmentManager.backStackEntryCount == 0
            }
    }
}