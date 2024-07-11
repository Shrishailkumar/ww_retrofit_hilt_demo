package com.zensar.retrofit_hilt_wwdemo.data.data.remote

import com.zensar.retrofit_hilt_wwdemo.data.remote.DogService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val dogService: DogService) {

    suspend fun getDog() =
        dogService.getDog()

}