package com.renatic.app.viewManager

import android.content.Intent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.renatic.app.ProfileActivity
import com.renatic.app.R

class ToolbarManager(private val activity: AppCompatActivity) {
    private val toolbar: Toolbar by lazy {
        activity.findViewById(R.id.toolbar)
    }

    private val toProfile: ImageView by lazy {
        activity.findViewById(R.id.iv_to_profile)
    }

    fun setupToolbar() {
        activity.setSupportActionBar(toolbar)

        toProfile.setOnClickListener {
            val intent = Intent(activity, ProfileActivity::class.java)
            activity.startActivity(intent)
        }
    }
}