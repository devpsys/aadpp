package me.devpsys.apps.gads2020.aadpp.app.ui.activities

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import me.devpsys.apps.gads2020.aadpp.app.R
import me.devpsys.apps.gads2020.aadpp.app.databinding.ActivitySubmitBinding

class SubmitActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivitySubmitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_submit)

        events()
    }

    private fun events() {
        mBinding.ibBack.setOnClickListener { onBackPressed() }
        mBinding.btnSubmit.setOnClickListener { processForm() }
    }

    private fun processForm() {
        val fname = mBinding.edtFName.text.toString().trim()
        val lname = mBinding.edtLName.text.toString().trim()
        val email = mBinding.edtEmail.text.toString().trim()
        val link = mBinding.edtLink.text.toString().trim()

        if (fname.isEmpty() || lname.isEmpty() || email.isEmpty() || link.isEmpty()) {
            show(message = "All fields are required.")
            return
        }

        if (!email.isValidEmail()) {
            show(message = "Enter a valid email address.")
            return
        }

        if (!link.isValidUrl()) {
            show(message = "Enter a valid URL.")
            return
        }

    }

    private fun String.isValidUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()

    private fun String.isValidEmail(): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun show(message: String) {
        Snackbar.make(mBinding.root, message, Snackbar.LENGTH_LONG).show()
    }
}