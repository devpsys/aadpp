package me.devpsys.apps.gads2020.aadpp.app.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import me.devpsys.apps.gads2020.aadpp.app.R
import me.devpsys.apps.gads2020.aadpp.app.databinding.ActivityMainBinding
import me.devpsys.apps.gads2020.aadpp.app.ui.main.SectionsPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        mBinding.viewPager.adapter = sectionsPagerAdapter

        mBinding.tabs.setupWithViewPager(mBinding.viewPager)

        mBinding.btnSubmit.setOnClickListener {
            startActivity(Intent(this, SubmitActivity::class.java))
        }

    }
}