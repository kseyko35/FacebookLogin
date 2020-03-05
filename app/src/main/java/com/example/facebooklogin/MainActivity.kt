package com.example.facebooklogin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import java.util.*


class MainActivity : AppCompatActivity(), FacebookCallback<LoginResult> {
    private var callbackManager: CallbackManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().logInWithReadPermissions(
            this,
            Arrays.asList("public_profile")
        )
        // If you are using in a fragment, call loginButton.setFragment(this);
        // If you are using in a fragment, call loginButton.setFragment(this);
        val btnFacebookLogin: LoginButton = this.findViewById(R.id.login_button)
        val callbackManager = CallbackManager.Factory.create()
        btnFacebookLogin.registerCallback(callbackManager, this)
//        loginButton = findViewById(R.id.login_button);
//        loginButton.setReadPermissions(Arrays.asList(EMAIL));

    }

    override fun onSuccess(result: LoginResult?) {
        print(result)
        Toast.makeText(this@MainActivity, "onSuccess", Toast.LENGTH_SHORT).show()
    }

    override fun onCancel() {
        Toast.makeText(this@MainActivity, "onCancel", Toast.LENGTH_SHORT).show()
    }

    override fun onError(error: FacebookException?) {
        Toast.makeText(this@MainActivity, "onError", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}

