package com.canberkbbc.kotlin_countries.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SharedPrefencesHelper {
    private val PREFERENCES_TIME = "time"
    companion object {
        private var sharedPrefences: SharedPreferences? = null
        @Volatile
        private var instance: SharedPrefencesHelper? = null
        private var lock = Any()
        operator fun invoke(context: Context): SharedPrefencesHelper =
            instance ?: synchronized(lock) {
                instance ?: makeSharedPrefencesHelper(context).also {
                    instance = it
                }
            }

        private fun makeSharedPrefencesHelper(context: Context): SharedPrefencesHelper {
            sharedPrefences = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPrefencesHelper()
        }
    }
    fun saveTime(time :Long){
        sharedPrefences?.edit(commit = true){
            putLong(PREFERENCES_TIME,time)
        }
    }
    fun getTime() = sharedPrefences?.getLong(PREFERENCES_TIME,0)
}