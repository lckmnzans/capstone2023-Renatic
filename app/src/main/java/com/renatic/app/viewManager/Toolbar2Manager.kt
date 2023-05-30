package com.renatic.app.viewManager

import android.content.Intent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.renatic.app.ProfileActivity
import com.renatic.app.R

class Toolbar2Manager(private val activity: AppCompatActivity) {
    private val toolbar: Toolbar by lazy {
        activity.findViewById(R.id.toolbar2)
    }

    private val toBack: ImageView by lazy {
        activity.findViewById(R.id.iv_back)
    }

    fun setupToolbar() {
        activity.setSupportActionBar(toolbar)

        toBack.setOnClickListener {
            activity.finish()
        }
    }
}