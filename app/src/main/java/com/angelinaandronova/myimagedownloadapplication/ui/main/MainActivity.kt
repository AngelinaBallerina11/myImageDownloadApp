package com.angelinaandronova.myimagedownloadapplication.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.angelinaandronova.myimagedownloadapplication.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

}
