package me.devpsys.apps.gads2020.aadpp.app.api

import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("hours")
    fun hours(
    ): Call<String>

    @GET("skilliq")
    fun skilliq(
    ): Call<String>
}