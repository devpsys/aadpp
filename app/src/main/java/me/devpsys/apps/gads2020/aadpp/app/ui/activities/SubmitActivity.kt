package me.devpsys.apps.gads2020.aadpp.app.ui.activities

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import me.devpsys.apps.gads2020.aadpp.app.R
import me.devpsys.apps.gads2020.aadpp.app.api.RetrofitClient
import me.devpsys.apps.gads2020.aadpp.app.databinding.ActivitySubmitBinding
import me.devpsys.apps.gads2020.aadpp.app.ui.fragments.AlertFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubmitActivity : AppCompatActivity(), AlertFragment.OnRequestConfirmationListener {

    private lateinit var lastName: String
    private lateinit var firstName: String
    private lateinit var emailAddress: String
    private lateinit var repoLink: String
    private lateinit var mBinding: ActivitySubmitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_submit)
        mBinding.loading = false
        mBinding.clearForm = false

        events()
    }

    override fun onConfirmed() {
        processForm()
    }

    private fun events() {
        mBinding.ibBack.setOnClickListener { onBackPressed() }
        mBinding.btnSubmit.setOnClickListener { confirm() }
    }

    private fun confirm() {
        firstName = mBinding.edtFName.text.toString().trim()
        lastName = mBinding.edtLName.text.toString().trim()
        emailAddress = mBinding.edtEmail.text.toString().trim()
        repoLink = mBinding.edtLink.text.toString().trim()

        if (firstName.isEmpty() || lastName.isEmpty() || emailAddress.isEmpty() || repoLink.isEmpty()) {
            show(message = "All fields are required.")
            return
        }

        if (!emailAddress.isValidEmail()) {
            show(message = "Enter a valid email address.")
            return
        }

        if (!repoLink.isValidUrl()) {
            show(message = "Enter a valid URL.")
            return
        }

        AlertFragment.newInstance("confirmation").show(supportFragmentManager, "")
    }

    private fun processForm() {
        mBinding.loading = true
        RetrofitClient.getFormInstance().submit(firstName, lastName, emailAddress, repoLink)
            .enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    mBinding.loading = false
                    AlertFragment.newInstance(if (response.isSuccessful) "success" else "failure")
                        .show(supportFragmentManager, "")
                    mBinding.clearForm = response.isSuccessful
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    mBinding.loading = false
                    AlertFragment.newInstance("failure").show(supportFragmentManager, "")
                }
            })

    }

    private fun String.isValidUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()

    private fun String.isValidEmail(): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun show(message: String) {
        Snackbar.make(mBinding.root, message, Snackbar.LENGTH_LONG).show()
    }

}