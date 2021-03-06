package com.example.loginfb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val analytics: FirebaseAnalytics= FirebaseAnalytics.getInstance(this)
        val bundle=Bundle()
        bundle.putString("message", "Integración Firebase completa Enaitz")
        analytics.logEvent("Initscreen", bundle)
    }
}