package com.example.groceryshopper.views

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.groceryshopper.UrlRequest.BASE_URL
import com.example.groceryshopper.UrlRequest.UPDATE_PROFILE_END_POINT
import com.example.groceryshopper.databinding.ActivityResetPasswordBinding
import org.json.JSONObject

class ResetPasswordActivity : AppCompatActivity() {
    lateinit var binding : ActivityResetPasswordBinding
    lateinit var sharedPref : SharedPreferences
    lateinit var requestQueue: RequestQueue
    lateinit var currentPassword: String
    lateinit var newPassword: String
    lateinit var confirmNewPassword: String
    lateinit var pb : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        requestQueue = Volley.newRequestQueue(this)
        sharedPref = getSharedPreferences("userDetails", MODE_PRIVATE)
        setupEvents()
    }

    private fun setupEvents() {
        binding.btnSave.setOnClickListener{
            currentPassword = binding.etNewPassword.text.toString()
            newPassword = binding.etNewPassword.text.toString()
            confirmNewPassword = binding.etConfirmNewPassword.text.toString()

            if (validateInput()) {
                if (validateCurrentPassword()) {
                    //showProgressBar()
                    //updateNewPassword()
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
        }
    }

    private fun showProgressBar() {
        pb = ProgressDialog(this).apply{
            setMessage("Updating password...")
            setCancelable(false)
            show()
        }
    }

    private fun validateCurrentPassword(): Boolean {
        val currentPassword = binding.etCurrentPassword.text.toString()
        if (currentPassword != sharedPref.getString("userPassword", "")) {
            Toast.makeText(baseContext, "Incorrect password", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    // API NOT SUPPORTED
    // update password based on _id
//    private fun updateNewPassword() {
//        val newPassword = binding.etNewPassword.text.toString()
//        val userId = sharedPref.getString("userId", "")
//        val jsonObject = JSONObject()
//        jsonObject.put("password", newPassword)
//        val changePasswordRequest = JsonObjectRequest(
//            Request.Method.PUT,
//            "${BASE_URL}${UPDATE_PROFILE_END_POINT}$userId",
//            jsonObject,
//            {
//                // update new password in sharedPref
//                sharedPref.edit().putString("userPassword", newPassword).apply()
//                pb.dismiss()
//                Toast.makeText(baseContext, "Successfully changed the password", Toast.LENGTH_SHORT).show()
//            }, {
//                pb.dismiss()
//                it.printStackTrace()
//                Toast.makeText(baseContext, "Failed to change password, please contact us.", Toast.LENGTH_SHORT).show()
//            }
//        )
//        requestQueue.add(changePasswordRequest)
//        finish()
//    }

    private fun validateInput(): Boolean{
        if (currentPassword.isEmpty()) {
            binding.etCurrentPassword.error = "Invalid password"
            return false
        }
        if (newPassword.isEmpty()) {
            binding.etNewPassword.error = "Invalid password"
            return false
        }
        if (confirmNewPassword.isEmpty()) {
            binding.etConfirmNewPassword.error = "Invalid password"
            return false
        }
        if (newPassword != confirmNewPassword) {
            binding.etNewPassword.error = "Password not matched"
            binding.etConfirmNewPassword.error = "Password not matched"
            return false
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}