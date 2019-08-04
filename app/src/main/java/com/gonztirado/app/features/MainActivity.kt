package com.gonztirado.app.features

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gonztirado.app.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)
    }

    companion object {
        fun callingIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
