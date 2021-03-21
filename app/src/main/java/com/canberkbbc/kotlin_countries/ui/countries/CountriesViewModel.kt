package com.canberkbbc.kotlin_countries.ui.countries

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.canberkbbc.kotlin_countries.core.BaseViewModel
import com.canberkbbc.kotlin_countries.data.remote.model.CountryModel
import com.canberkbbc.kotlin_countries.service.CountryApiService
import com.canberkbbc.kotlin_countries.service.CountryDatabase
import com.canberkbbc.kotlin_countries.utils.SharedPrefencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class CountriesViewModel(application: Application) : BaseViewModel(application) {

    private val countryApiService = CountryApiService()
    private val disposable = CompositeDisposable()
    private var customPref = SharedPrefencesHelper(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    val countries = MutableLiveData<List<CountryModel>>()
    val countryError = MutableLiveData<Boolean>()
    val countreyLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        countreyLoading.value = true;
        val updateTime = customPref.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQLite()
        } else {
            getDataFromApi()
        }
    }

    fun refresgFromAPI() {
        getDataFromApi()
    }

    private fun getDataFromApi() {
        countreyLoading.value = true;
        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<CountryModel>>() {
                    override fun onSuccess(t: List<CountryModel>) {
                        storeInSQLite(t)
                        Toast.makeText(getApplication(), "Countries From API", Toast.LENGTH_LONG)
                            .show()
                    }

                    override fun onError(e: Throwable) {
                        countreyLoading.value = false
                        countryError.value = true
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun getDataFromSQLite() {
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "Countries From SQLite", Toast.LENGTH_LONG).show()
        }
    }


    private fun showCountries(list: List<CountryModel>) {
        countries.value = list;
        countryError.value = false
        countreyLoading.value = false
    }

    private fun storeInSQLite(list: List<CountryModel>) {
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listlong = dao.insertAll(*list.toTypedArray()) //->individual
            var i = 0;
            for (item in list) {
                item.uuid = listlong[i].toInt()
                i++
            }
            showCountries(list)
        }
        customPref.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}