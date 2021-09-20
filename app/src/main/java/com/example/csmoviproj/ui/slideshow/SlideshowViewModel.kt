package com.example.csmoviproj.ui.slideshow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.csmoviproj.R
import com.example.csmoviproj.Score
import com.example.csmoviproj.databinding.FragmentSlideshowBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet


class SlideshowViewModel : AppCompatActivity() {

    private lateinit var lineChart: LineChart
    private var scoreList = ArrayList<Score>()
    private lateinit var binding: FragmentSlideshowBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = FragmentSlideshowBinding.inflate(layoutInflater)
        val view = binding.root
setContentView(view)

        initLineChart()


        setDataToLineChart()

    }

    private fun initLineChart() {
        TODO("Not yet implemented")
    }


    private fun setDataToLineChart() {
        val xvalue = ArrayList<String>()
        xvalue.add("11.00 AM")
        xvalue.add("11.00 AM")
        xvalue.add("11.00 AM")
        xvalue.add("11.00 AM")
        xvalue.add("11.00 AM")

        val lineentry = ArrayList<Entry>()
        lineentry.add(Entry(20f,0))
        lineentry.add(Entry(20f,1))
        lineentry.add(Entry(20f,2))
        lineentry.add(Entry(20f,3))
        lineentry.add(Entry(20f,4))

        val linedataset = LineDataSet(lineentry, "First")
        linedataset.color= resources.getColor((R.color.purple_200))

val data = LineData(xvalue, linedataset)
        lineChart.data = data
        lineChart.setBackgroundColor(resources.getColor(R.color.white))
        lineChart.animateXY(3000, 3000)
    }



}