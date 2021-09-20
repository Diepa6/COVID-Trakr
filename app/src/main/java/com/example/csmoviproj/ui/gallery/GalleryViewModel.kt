package com.example.csmoviproj.ui.gallery


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.csmoviproj.CovidData
import com.example.csmoviproj.CovidService
import com.example.csmoviproj.CovidSparkAdapter
import com.example.csmoviproj.databinding.FragmentGalleryBinding
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_gallery.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


class GalleryViewModel : AppCompatActivity(){
    private lateinit var nationalDailyData: List<CovidData>
    private lateinit var adapter: CovidSparkAdapter
    private lateinit var currentlyShownData: List<CovidData>
    private lateinit var binding: FragmentGalleryBinding
    companion object {
        const val TAG = "GalleryViewModel"
        const val BASE_URL = "https://covidtracking.com/api/v2/"
        const val ALL_STATES = "All (Nationwide)"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentGalleryBinding.inflate(layoutInflater)
        val view: ConstraintLayout = binding.root
        setContentView(view)
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val covidService = retrofit.create(CovidService::class.java)
        covidService.getNationalData().enqueue(object : Callback<List<CovidData>> {
            override fun onFailure(call: Call<List<CovidData>>, t: Throwable) {
                Log.e(TAG, "onFailure $t")
            }

            override fun onResponse(
                call: Call<List<CovidData>>,
                response: Response<List<CovidData>>
            ) {
                Log.i(TAG, "onResponse $response")
                val nationalData = response.body()
                if (nationalData == null) {
                    Log.w(TAG, "Did not receive a valid response body")
                    return
                }

                nationalDailyData = nationalData.reversed()
                Log.i(TAG, "Update graph with national data")
                // update graph with national data
                updateDisplayWithData(nationalDailyData)
            }
        })
    }

        private fun updateDisplayWithData(dailyData: List<CovidData>){
            currentlyShownData = dailyData
            // Retrieve data from spark aadapter
            adapter = CovidSparkAdapter(dailyData)
            sparkview.adapter = adapter
            // update radio buttons for time and cases
            radioButtonPositive.isChecked = true
            radioButtonMax.isChecked = true
            // display metric for most recent date
           updateInfoForDate(dailyData.last())
        }

    private fun updateInfoForDate(covidData: CovidData) {
        tvMetricLabel.text = NumberFormat.getInstance().format(covidData.positiveIncrease)
         val outputDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)
        tvDateLabel.text = outputDateFormat.format(covidData.dateChecked)
    }



}



