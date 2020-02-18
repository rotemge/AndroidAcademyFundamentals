package com.android.academy.model

import com.android.academy.AppExecutors
import com.android.academy.database.DatabaseModule
import com.android.academy.networking.NetworkModule
import com.android.academy.networking.VideosListResult
import com.android.academy.networking.toVideoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrailerRepository {

    fun fetchTrailer(movieId: Int, listener: Listener<VideoModel>) {
        AppExecutors.diskIO.execute {
            val trailerModel = DatabaseModule.videoDao?.getVideo(movieId)
            if (trailerModel == null) {
                getRemoteTrailer(movieId, listener)
            } else {
                listener.onSuccess(trailerModel)
            }
        }
    }

    private fun getRemoteTrailer(movieId: Int, listener: Listener<VideoModel>) {
        NetworkModule.moviesClient.loadMovieTrailer(movieId).enqueue(object : Callback<VideosListResult> {
            override fun onResponse(call: Call<VideosListResult>, response: Response<VideosListResult>) {
                val video = response.body()?.toVideoModel()
                if (video != null) {
                    listener.onSuccess(video)
                    saveTrailerResultToDb(video)
                }
            }

            override fun onFailure(call: Call<VideosListResult>, t: Throwable) {
                listener.onFailure(t)
            }
        })
    }

    private fun saveTrailerResultToDb(videoModel: VideoModel) {
        AppExecutors.diskIO.execute {
            DatabaseModule.videoDao?.insert(videoModel)
        }
    }

    interface Listener<T> {
        fun onSuccess(element: T)
        fun onFailure(error: Throwable)
    }

}