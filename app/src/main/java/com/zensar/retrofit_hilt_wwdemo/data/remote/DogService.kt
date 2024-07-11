package com.zensar.retrofit_hilt_wwdemo.data.remote

import com.zensar.retrofit_hilt_wwdemo.model.DogResponse
import com.zensar.retrofit_hilt_wwdemo.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface DogService {

    @GET(Constants.RANDOM_URL)
    suspend fun getDog(): Response<DogResponse>
}
