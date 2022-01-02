package com.abuteam.finalyearproject.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.abuteam.finalyearproject.R
import com.abuteam.finalyearproject.sessions.AppSessions
import com.abuteam.finalyearproject.sessions.Values
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {
    private var backBtn: ImageView? = null
    private var fullName: EditText? = null
    private var mail: EditText? = null
    private var pass: EditText? = null
    private var confPass: EditText? = null
    private var createAccount: Button? = null
    private var errorText: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        backBtn = findViewById<View>(R.id.backBtn) as ImageView
        fullName = findViewById<View>(R.id.nameEdt) as EditText
        mail = findViewById<View>(R.id.emailEdt) as EditText
        pass = findViewById<View>(R.id.checkPass) as EditText
        confPass = findViewById<View>(R.id.comfirmPass) as EditText
        errorText = findViewById<View>(R.id.errorText) as TextView
        createAccount = findViewById<View>(R.id.createAccount) as Button
        createAccount!!.setOnClickListener {
            if (validationInfo()) {
                if (checkValidation()) {
                    val i = Intent(this@SignupActivity, LoginActivity::class.java)
                    putData()
                    startActivity(i)
                    finish()
                }
            } else {
                Toast.makeText(this@SignupActivity, "Do not empty fields", Toast.LENGTH_SHORT).show()
            }
        }
        backBtn!!.setOnClickListener {
            val i = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun validationInfo(): Boolean {
        var flag = true
        val name = fullName!!.text.toString()
        val email = mail!!.text.toString()
        val password = pass!!.text.toString()
        val confirm = confPass!!.text.toString()
        if (name.isEmpty()) {
            fullName!!.error = "Required Field"
            flag = false
        }
        if (email.isEmpty()) {
            mail!!.error = "Required Field"
            flag = false
        }
        if (password.isEmpty()) {
            pass!!.error = "Required Field"
            flag = false
        }
        if (confirm.isEmpty()) {
            confPass!!.error = "Required Field"
            flag = false
        }
        return flag
    }

    private fun checkValidation(): Boolean {
        var flag = true
        val value = mail!!.text.toString()
        val pass = pass!!.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            mail!!.error = "Invalid Address"
            flag = false
        }
        if (!isValidPassword(pass)) {
            this.pass!!.error = "Characters required, please try again.!"
            errorText!!.visibility = View.VISIBLE
            flag = false
        }
        if (this.pass!!.text.toString() != confPass!!.text.toString()) {
            confPass!!.error = "Password mismatched"
            flag = false
        }
        return flag
    }

    fun isValidPassword(password: String?): Boolean {
        val pattern: Pattern
        val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
        pattern = Pattern.compile(PASSWORD_PATTERN)
        val matcher: Matcher = pattern.matcher(password)
        return matcher.matches()
    }

    private fun putData() {
        AppSessions.setSession(Values.preference.key.USERNAME_KEY ,mail?.text.toString(),this);
        AppSessions.setSession(Values.preference.key.PASSWORD_KEY,pass?.text.toString(),this);
    }
}