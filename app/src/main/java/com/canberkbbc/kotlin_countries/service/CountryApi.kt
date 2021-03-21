package com.canberkbbc.kotlin_countries.service

import com.canberkbbc.kotlin_countries.data.remote.model.CountryModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface CountryApi {
    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries(): Single<List<CountryModel>>
}