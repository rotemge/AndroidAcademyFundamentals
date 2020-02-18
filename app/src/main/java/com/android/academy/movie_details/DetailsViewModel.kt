package com.android.academy.movie_details

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.academy.model.TrailerRepository
import com.android.academy.model.VideoModel

class DetailsViewModel : ViewModel() {

    private val repository = TrailerRepository()

    private val loadingMutableLiveData = MutableLiveData<Boolean>()
    private val trailerKeyMutableLiveData = MutableLiveData<String>()

    fun loadingLiveData(): LiveData<Boolean> = loadingMutableLiveData
    fun trailerKeyLiveData(): LiveData<String> = trailerKeyMutableLiveData

    fun fetchTrailer(movieId: Int) {
        loadingMutableLiveData.value = true
        repository.fetchTrailer(movieId, object : TrailerRepository.Listener<VideoModel> {
            override fun onSuccess(element: VideoModel) {
                loadingMutableLiveData.postValue(false)
                trailerKeyMutableLiveData.postValue(element.key)
            }

            override fun onFailure(error: Throwable) {
                loadingMutableLiveData.postValue(false)
            }
        })
    }

}