package com.zensar.retrofit_hilt_wwdemo.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.viewModels
import android.widget.Toast
import coil.load
import coil.transform.RoundedCornersTransformation
import com.zensar.retrofit_hilt_wwdemo.R
import com.zensar.retrofit_hilt_wwdemo.utils.NetworkResult
import com.zensar.retrofit_hilt_wwdemo.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

/*
Hilt can provide dependencies to other Android classes that have the @AndroidEntryPoint annotation:
Application (by using @HiltAndroidApp),ViewModel (by using @HiltViewModel),Activity, Fragment,
Service,View,BroadCastReciver	note:If you annotate an Android class with @AndroidEntryPoint,
then you also must annotate Android classes that depend on it.
 */
@AndroidEntryPoint
class WWRerofitCachingHiltDemoActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    lateinit var imageView: ImageView
    lateinit var imageDog: ImageView
    lateinit var pb: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ww_caching_hilt_demo_app)

        imageView = findViewById(R.id.imgRefresh)
        imageDog = findViewById(R.id.imgDog)
        pb = findViewById(R.id.pbDog)
        imageView.setOnClickListener {
            fetchResponse()
        }
        fetchData()
    }


    private fun fetchData() {
        fetchResponse()
        mainViewModel.response.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let {
                        imageDog.load(
                            response.data.message
                        ) {
                            transformations(RoundedCornersTransformation(16f))
                        }
                    }
                    pb.visibility = View.GONE
                }

                is NetworkResult.Error -> {
                    pb.visibility = View.GONE
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    pb.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun fetchResponse() {
        mainViewModel.fetchDogResponse()
        //    pb.visibility = View.VISIBLE
    }
}