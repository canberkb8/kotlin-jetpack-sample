package com.canberkbbc.kotlin_countries.ui.countrydetail

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.canberkbbc.kotlin_countries.core.BaseViewModel
import com.canberkbbc.kotlin_countries.data.remote.model.CountryModel
import com.canberkbbc.kotlin_countries.service.CountryDatabase
import kotlinx.coroutines.launch

class CountryDetailViewModel(application: Application): BaseViewModel(application) {
    val countryDetailLiveData = MutableLiveData<CountryModel>()
    fun getDataFromRoom(uuid:Int){
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val country = dao.getSingleCountries(uuid)
            countryDetailLiveData.value = country
        }
    }
}