package com.zensar.retrofit_hilt_wwdemo.data

import com.zensar.retrofit_hilt_wwdemo.data.data.remote.RemoteDataSource
import com.zensar.retrofit_hilt_wwdemo.model.BaseApiResponse
import com.zensar.retrofit_hilt_wwdemo.model.DogResponse
import com.zensar.retrofit_hilt_wwdemo.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {

    suspend fun getDog(): Flow<NetworkResult<DogResponse>> {

        return flow<NetworkResult<DogResponse>> {
                emit(safeApiCall { remoteDataSource.getDog()
                         })
            }.flowOn(Dispatchers.IO)

    }

}
