package com.saimhassan.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    var firebaseUser:FirebaseUser?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        button_welcome_register.setOnClickListener{
            val intent=Intent(this@WelcomeActivity,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        button_login_register.setOnClickListener {
            val intent=Intent(this@WelcomeActivity,LogInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null)
        {
            val intent=Intent(this@WelcomeActivity,MainActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
}