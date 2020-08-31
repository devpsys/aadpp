package me.devpsys.apps.gads2020.aadpp.app.api

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitClient {
    companion object {
        private var INSTANCE: Api? = null
        private var FORM_INSTANCE: Api? = null
        private const val BASE_URL = "https://gadsapi.herokuapp.com/api/"

        fun getInstance(): Api {
            Log.d("Response", "fun getInstance(): Api {")

            if (INSTANCE == null) {
                Log.d("Response", "instantiating...")
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build()
                INSTANCE = retrofit.create(
                    Api::class.java
                )
            }
            return INSTANCE!!
        }

        fun getFormInstance(): Api {
            Log.d("Response", "fun getFormInstance(): Api {")
            if (FORM_INSTANCE == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://docs.google.com/forms/d/e/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build()
                FORM_INSTANCE = retrofit.create(
                    Api::class.java
                )
            }
            return FORM_INSTANCE!!
        }
    }
}