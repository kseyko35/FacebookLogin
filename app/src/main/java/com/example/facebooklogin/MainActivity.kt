package com.example.facebooklogin

/**     Code with ❤
╔════════════════════════════╗
║   Created by Seyfi ERCAN   ║
╠════════════════════════════╣
║  seyfiercan35@hotmail.com  ║
╠════════════════════════════╣
║      07,March,2020         ║
╚════════════════════════════╝
 */

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity(), FacebookCallback<LoginResult> {
    private var callbackManager: CallbackManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnFacebookLogin: LoginButton = findViewById(R.id.login_button)


        callbackManager = CallbackManager.Factory.create()
        btnFacebookLogin.registerCallback(callbackManager, this)


        var tokenTracker: AccessTokenTracker = object : AccessTokenTracker() {
            override fun onCurrentAccessTokenChanged(
                oldAccessToken: AccessToken?,
                currentAccessToken: AccessToken?
            ) {
                if (currentAccessToken == null) {
                    name.text = ""
                    profile_image.setImageResource(R.mipmap.ic_launcher)
                    Toast.makeText(this@MainActivity, "User Loged out", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


    override fun onSuccess(result: LoginResult?) {

        val request = GraphRequest.newMeRequest(result?.accessToken) { `object`, response ->
            displayUserInfo(`object`)

        }

        val parameters = Bundle()
        parameters.putString("fields", "name,id")
        request.parameters = parameters
        request.executeAsync()
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

    private fun displayUserInfo(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            val circleImageView: CircleImageView = findViewById(R.id.profile_image)
            val nameTxtView: TextView = findViewById(R.id.name)

            Glide.with(this)
                .load("http://graph.facebook.com/" + jsonObject.getString("id") + "/picture?type=large")
                .into(circleImageView)
            nameTxtView.text = jsonObject.getString("name")

        }
    }


}

