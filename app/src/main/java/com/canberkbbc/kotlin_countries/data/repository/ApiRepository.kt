package com.canberkbbc.kotlin_countries.data.repository

import com.canberkbbc.kotlin_countries.data.remote.api.ApiInterface

class ApiRepository constructor(private val apiInterface: ApiInterface) {

    suspend fun getCountries() =  apiInterface.getTeams()

}