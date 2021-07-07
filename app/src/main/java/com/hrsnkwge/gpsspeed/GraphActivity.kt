package com.hrsnkwge.gpsspeed

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.hrsnkwge.gpsspeed.databinding.ActivityGraphBinding
import java.io.File

class GraphActivity :  AppCompatActivity() {
    private lateinit var binding: ActivityGraphBinding

    private var mTypeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
    // データの個数
    private var chartDataCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraphBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val lineChart : LineChart = binding.lineChart

        //Setup
        lineChart.apply {
            description.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            // 拡大縮小可能
            isScaleXEnabled = true
            setPinchZoom(false)
            setDrawGridBackground(false)

            //データラベルの表示
            legend.apply {
                form = Legend.LegendForm.LINE
                typeface = mTypeface
                textSize = 11f
                textColor = Color.WHITE
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                orientation = Legend.LegendOrientation.HORIZONTAL
                setDrawInside(false)
            }

            //y軸右側の設定
            axisRight.isEnabled = false

            //X軸表示
            xAxis.apply {
                typeface = mTypeface
                setDrawLabels(false)
                // 格子線を表示する
                setDrawGridLines(true)
            }

            //y軸左側の表示
            axisLeft.apply {
                typeface = mTypeface
                textColor = Color.WHITE
                // 格子線を表示する
                setDrawGridLines(true)
            }
        }


        lineChart.data = lineData(chartDataCount, 100f)

    }

    private fun lineData(count: Int, range: Float):LineData {

        val values = mutableListOf<Entry>()
        val path = getExternalFilesDir(null)
        val folder = File(path,"")
        folder.mkdirs()
        val file = File(folder, intent.getStringExtra("filename"))
        Log.d("Path:",file.absolutePath)
        val datas = file.readLines()
        chartDataCount = datas.size
        for (i in 0 until chartDataCount) {
            values.add(Entry(i.toFloat(), datas[i].toFloat()))
        }


        // グラフのレイアウトの設定
        val yVals = LineDataSet(values, "Speed").apply {
            axisDependency =  YAxis.AxisDependency.LEFT
            color = Color.WHITE
            // タップ時のハイライトカラー
            highLightColor = Color.YELLOW
            setDrawCircles(true)
            setDrawCircleHole(true)
            // 点の値非表示
            setDrawValues(false)
            // 線の太さ
            lineWidth = 2f
        }
        val data = LineData(yVals)
        return data
    }


}