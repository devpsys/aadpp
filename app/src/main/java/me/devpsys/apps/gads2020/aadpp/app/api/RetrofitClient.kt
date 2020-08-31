package me.devpsys.apps.gads2020.aadpp.app.api

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitClient {
    companion object {
        private var INSTANCE: Api? = null
        private const val BASE_URL = "https://gadsapi.herokuapp.com/api/"

        fun getInstance(): Api {
            if (INSTANCE == null) {
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
    }
}