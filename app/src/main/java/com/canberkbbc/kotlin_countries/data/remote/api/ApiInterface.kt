package com.canberkbbc.kotlin_countries.data.remote.api

import com.canberkbbc.kotlin_countries.data.remote.model.CountryModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("countrydataset")
    suspend fun getTeams(): Response<CountryModel>

}