package com.chenyangqi.business

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.chenyangqi.router.annotations.Destination
import com.chenyangqi.router_runtime.Router

@Destination(url = "router://page_login", description = "登录页面")
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tvUserName = findViewById<TextView>(R.id.tvUsername)

        val username = intent.extras?.getString("username")
        username.let {
            tvUserName.text = "username:$username"
        }

        findViewById<Button>(R.id.btnToHome).setOnClickListener {
            Router.go(this, "router://home_page")
        }
    }
}