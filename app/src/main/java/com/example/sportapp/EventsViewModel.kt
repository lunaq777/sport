package com.example.sportapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sportapp.networking.SportEventsAPI
import com.example.sportapp.networking.SportResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(private val sportEventsAPI: SportEventsAPI) : ViewModel() {

    private val _data = MutableLiveData<List<SportResponse>>()
    val data: LiveData<List<SportResponse>>
        get() = _data

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _isShowProgress = MutableLiveData<Boolean>()
    val isShowProgress: LiveData<Boolean>
        get() = _isShowProgress
    @RequiresApi(Build.VERSION_CODES.M)
    fun init(context: Context) {
            viewModelScope.launch {
                _isShowProgress.postValue(true)
                if (!checkInternetConnection(context)) {
                    _isShowProgress.postValue(true)
                    _errorMessage.postValue(context.getString(R.string.no_internet))
                } else {
                    val response = sportEventsAPI.getEvents()
                    if (response.isSuccessful) {
                        _isShowProgress.postValue(false)
                        _data.postValue(response.body())
                    } else {
                        _isShowProgress.postValue(true)
                        _errorMessage.postValue(response.message())
                    }
                }
            }
        }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetwork?.let { network ->
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } ?: return false
    }
}