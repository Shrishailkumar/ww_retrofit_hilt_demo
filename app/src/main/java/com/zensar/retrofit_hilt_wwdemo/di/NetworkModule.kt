package com.zensar.retrofit_hilt_wwdemo.di

import com.zensar.retrofit_hilt_wwdemo.WWRetrofitCachingHiltDemoApp
import com.zensar.retrofit_hilt_wwdemo.data.remote.DogService
import com.zensar.retrofit_hilt_wwdemo.utils.Constants.Companion.BASE_URL
import com.zensar.retrofit_hilt_wwdemo.utils.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/*
A Hilt module::Sometimes a type cannot be constructor-injected. This can happen for multiple reasons.
 For example, you cannot constructor-inject an interface.
	A hilt module is a class that is annotated with @Module.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /*
    Inject instances with @Provides: Constructor injection is also not possible if you don't own the class because it comes from an external library (classes like
	Retrofit, OkHttpClient, or Room databases), or if instances must be created with the builder pattern.
     */
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            //for caching the response add the CacheInterceptor & ForceCacheInterceptor
            .cache(Cache(File(WWRetrofitCachingHiltDemoApp.appContext!!.cacheDir, "http-cache"), 10L * 1024L * 1024L)) // 10 MiB
            .addNetworkInterceptor(Utils.CacheInterceptor()) // only if Cache-Control header is not enabled from the server
            .addInterceptor(Utils.ForceCacheInterceptor())
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
         GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideCurrencyService(retrofit: Retrofit): DogService =
        retrofit.create(DogService::class.java)

}