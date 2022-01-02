package com.abuteam.finalyearproject.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abuteam.finalyearproject.R
import com.abuteam.finalyearproject.sessions.AppSessions
import com.abuteam.finalyearproject.sessions.Values
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private var mSigninButton: Button? = null
    private var mloginButton: Button? = null
    private var userName: EditText? = null
    private var userPass: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        mSigninButton = findViewById(R.id.signupBtn)
        mloginButton = findViewById(R.id.loginBtn)
        userName = findViewById(R.id.userNameEdt)
        userPass = findViewById(R.id.passwordEdt)
        generate()

    }

    private fun generate() {
        mloginButton!!.setOnClickListener {

            if (validationInfo()) {
                if (checkValidation()) {
                    if (getData()) {
                        AppSessions.setSession(Values.preference.key.IS_LOGGED_IN,true,this)
                        val i =  Intent(this, MainActivity::class.java)
                        startActivity(i)
                        finish()
                    }else{
                        Toast.makeText(this@LoginActivity, "Please check the details", Toast.LENGTH_SHORT)
                                .show()
                    }
                }
            } else {
                Toast.makeText(this@LoginActivity, "Please check the details", Toast.LENGTH_SHORT)
                        .show()
            }
        }

        mSigninButton?.setOnClickListener(){
            val i = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    //mainCallFun
    private fun validationInfo(): Boolean {
        var flag = true
        val usernameString = userName!!.text.toString()
        val passwordString = userPass!!.text.toString()
        if (usernameString.isEmpty()) {
            userName!!.error = "Required Field"
            flag = false
        }
        if (passwordString.isEmpty()) {
            userPass!!.error = "Required Field"
            flag = false
        }
        return flag
    }

    //DialogProgress
    private fun onProgress(message:String){
        val progress = ProgressDialog(this@LoginActivity)
        Handler(Looper.getMainLooper()).postDelayed({
            progress.setMessage(message)
            progress.show()
        },3000)
        progress.dismiss()
    }

    private fun checkValidation(): Boolean {
        var flag = true
        val value = userName!!.text.toString()
        val pass = userPass!!.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            userName!!.error = "You have entered a invalid E-mail\nPlease try again.!"
            flag = false
        }
        if (!isValidPassword(pass)) {
            userPass!!.error = "Characters required, please try again.!"
            flag = false
        }
        return flag
    }

    private fun isValidPassword(password: String?): Boolean {
        val pattern: Pattern
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher: Matcher = pattern.matcher(password.toString())
        return matcher.matches()
    }
    private fun getData(): Boolean {

        val editUser: String = userName?.text.toString()

        val sessionUserName = AppSessions.getSession(
                Values.preference.key.USERNAME_KEY,
                this
        )

        val editPassword: String = userPass?.text.toString()

        val sessionPassword = AppSessions.getSession(
                Values.preference.key.PASSWORD_KEY,
                this
        )
        return (editUser.equals(sessionUserName, ignoreCase = false)
                && editPassword.equals(sessionPassword, ignoreCase = false))
    }
}