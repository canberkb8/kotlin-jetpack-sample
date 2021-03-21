package com.canberkbbc.kotlin_countries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.canberkbbc.kotlin_countries.data.remote.model.CountryModel

@Dao
interface CountryDao {
    //coroutine de suspend durdurup devam ettırmeye yarayan
    //vararg multiple country objects
    //List<Long> primary keyleri döndürüyor
    @Insert
    suspend fun insertAll(vararg countries:CountryModel) : List<Long>

    @Query("SELECT * FROM countrymodel")
    suspend fun getAllCountries():List<CountryModel>

    @Query("SELECT * FROM countrymodel WHERE uuid=:countryId")
    suspend fun getSingleCountries(countryId:Int):CountryModel

    @Query("DELETE FROM countrymodel")
    suspend fun deleteAllCountries()
}