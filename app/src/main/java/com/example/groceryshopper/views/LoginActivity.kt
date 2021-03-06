package com.example.groceryshopper.views

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryshopper.UrlRequest.BASE_URL
import com.example.groceryshopper.UrlRequest.LOGIN_END_POINT
import com.example.groceryshopper.databinding.ActivityLoginBinding
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    lateinit var requestQueue: RequestQueue
    lateinit var email: String
    lateinit var password: String
    lateinit var pd: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestQueue = Volley.newRequestQueue(baseContext)

        setupEvents()
    }

    private fun setupEvents() {
        binding.btnLogin.setOnClickListener {
            email = binding.etLoginEmail.text.toString()
            password = binding.etLoginPassword.text.toString()

            if (!isValid()) {
                return@setOnClickListener
            }
            showProgressDialog()
            val userAuthentication = JSONObject()
            userAuthentication.put("email", email)
            userAuthentication.put("password", password)

            val userAuthenticator = JsonObjectRequest(
                Request.Method.POST,
                "$BASE_URL$LOGIN_END_POINT",
                userAuthentication,
                {
                    pd.dismiss()
                    val sharePref = getSharedPreferences("userDetails", MODE_PRIVATE)
                    val editor = sharePref.edit()
                    val userName = it.getJSONObject("user").getString("firstName")
                    val mobilePhone = it.getJSONObject("user").getString("mobile")
                    val userId = it.getJSONObject("user").getString("_id")
                    editor.putString("userName", userName)
                    editor.putString("userEmail", email)
                    editor.putString("userPassword", password)
                    editor.putString("mobilePhone", mobilePhone)
                    editor.putString("userId", userId)
                    editor.apply()
                    startActivity(Intent(baseContext, CategoryActivity::class.java))
                }, {
                    pd.dismiss()
                    it.printStackTrace()
                    Toast.makeText(
                        baseContext,
                        "Incorrect username or password, please try again",
                        Toast.LENGTH_LONG
                    ).show()
                }
            )
            requestQueue.add(userAuthenticator)
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(baseContext, RegisterActivity::class.java))
        }

        binding.tvLoginForgotPassword.setOnClickListener {
            startActivity(Intent(baseContext, ForgotPasswordActivity::class.java))
        }
        binding.btnFacebook.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("Notification")
                setMessage("This feature has not implemented yet")
                setPositiveButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                }
                create()
            }.show()
        }

        binding.btnGoogle.setOnClickListener {
            AlertDialog.Builder(this).apply {
                setTitle("Notification")
                setMessage("This feature has not implemented yet")
                setPositiveButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                }
                create()
            }.show()
        }
    }

    private fun isValid(): Boolean {
        if (email.isEmpty()) {
            binding.etLoginEmail.error = "Email cannot be blank"
            return false
        }
        if (password.isEmpty()) {
            binding.etLoginPassword.error = "Password cannot be blank"
            return false
        }
        return true
    }


    private fun showProgressDialog() {
        pd = ProgressDialog(this).apply {
            setMessage("Logging in...")
            setCancelable(false)
            show()
        }
    }
}