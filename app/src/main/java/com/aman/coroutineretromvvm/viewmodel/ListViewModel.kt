package com.aman.coroutineretromvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aman.coroutineretromvvm.model.CountriesService
import com.aman.coroutineretromvvm.model.Country
import kotlinx.coroutines.*

class ListViewModel : ViewModel() {

    val countryService = CountriesService.getCountriesService()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception : ${throwable.localizedMessage}")
    }
    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()
    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch() {
            val response = countryService.getCountriesList()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    countries.value = response.body()
                    countryLoadError.value = ""
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }


    private fun onError(message: String) {
        countryLoadError.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}