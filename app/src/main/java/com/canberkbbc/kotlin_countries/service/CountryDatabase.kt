package com.canberkbbc.kotlin_countries.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.canberkbbc.kotlin_countries.data.remote.model.CountryModel

@Database(entities = arrayOf(CountryModel::class), version = 1, exportSchema = false)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao

    //singelton
    companion object {
        //volatile farklı threadlere görünür hale gelır
        //invoke varmı yok mu kontrol eder devam eder ?: else if
        //synchronized aynı anda tek threadde işlem yapılcak (conflick olmaması için)
        @Volatile private var instance: CountryDatabase? = null
        private var lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context)=Room.databaseBuilder(
            context.applicationContext,
            CountryDatabase::class.java,
            "countrydatabase"
        ).build()
    }
}
