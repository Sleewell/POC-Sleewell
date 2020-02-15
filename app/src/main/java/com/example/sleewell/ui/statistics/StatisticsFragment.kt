package com.example.sleewell.ui.statistics

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.sleewell.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_statistics.view.*
import java.util.*


class StatisticsFragment : Fragment() {

    private lateinit var staisticsViewModel: StatisticsViewModel
    private lateinit var chart : PieChart
    private lateinit var chartBar : BarChart

    protected val parties = arrayOf(
        "Sommeil profond",
        "Sommeil léger",
        "Eveil"
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        staisticsViewModel =
            ViewModelProviders.of(this).get(StatisticsViewModel::class.java)
        val root = inflater.inflate(com.example.sleewell.R.layout.fragment_statistics, container, false)
        chart = root.findViewById(com.example.sleewell.R.id.chart) as PieChart
        chartBar = root.findViewById(com.example.sleewell.R.id.chartBar) as BarChart
        initGraphPie()
        initGraphBar()
        return root
    }

    @SuppressLint("ResourceType")
    private fun initGraphPie()
    {

        //chart.setBackgroundColor(Color.WHITE)

        chart.setUsePercentValues(true)
        chart.description.isEnabled = false

        //chart.setCenterTextTypeface(tfLight)
        chart.centerText = generateCenterSpannableText()

        chart.isDrawHoleEnabled = true
        chart.setHoleColor(Color.parseColor(resources.getString(R.color.BleuFoncé)))

        chart.setTransparentCircleColor(R.color.colorPrimaryDark)
        chart.setTransparentCircleAlpha(0)

        chart.holeRadius = 50f
        chart.transparentCircleRadius = 61f

        chart.setDrawCenterText(true)

        chart.isRotationEnabled = false
        chart.isHighlightPerTapEnabled = true

        chart.maxAngle = 180f // HALF CHART
        chart.rotationAngle = 180f
        chart.setCenterTextOffset(0f, -20f)

        setDataPie(3, 100f)

        chart.animateY(1400, Easing.EaseInOutQuad)

        val l = chart.legend
        l.isEnabled = false

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE)
        //chart.setEntryLabelTypeface(tfRegular)
        chart.setEntryLabelTextSize(12f)

        chart.invalidate()
    }

    private fun setDataPie(count: Int, range: Float) {

        val values = ArrayList<PieEntry>()

        for (i in 0 until count) {
            values.add(
                PieEntry(
                    (Math.random() * range + range / 5).toFloat(),
                    parties[i]
                )
            )
        }

        val dataSet = PieDataSet(values, "Election Results")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f

        dataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
        //dataSet.setSelectionShift(0f);

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        //data.setValueTypeface(tfLight)
        chart.data = data

        chart.invalidate()
    }
    
    private fun generateCenterSpannableText(): SpannableString {

        val s = SpannableString("Sommeil récent")
        s.setSpan(RelativeSizeSpan(1.7f), 0, s.length, 0)
        s.setSpan(StyleSpan(Typeface.NORMAL), 0, s.length, 0)
        s.setSpan(ForegroundColorSpan(Color.WHITE), 0, s.length, 0)
        return s
    }

    private fun initGraphBar() {
        chartBar.description.isEnabled = false
        chartBar.setMaxVisibleValueCount(24)
        chartBar.setPinchZoom(false)
        chartBar.setDrawBarShadow(false)
        chartBar.setDrawGridBackground(false)

        val xAxis = chartBar.xAxis
        xAxis.isEnabled = false
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)

        chartBar.axisLeft.axisMinimum = 0.0f
        chartBar.axisRight.axisMinimum = 0.0f

        chartBar.axisRight.setDrawZeroLine(true)
        chartBar.axisLeft.setDrawZeroLine(true)

        chartBar.axisLeft.setDrawGridLines(false)
        chartBar.axisLeft.textColor = Color.WHITE
        chartBar.axisRight.setDrawGridLines(false)
        chartBar.axisRight.textColor = Color.WHITE

        // add a nice and smooth animation
        chartBar.animateY(1000)

        chartBar.legend.isEnabled = false


        val values = ArrayList<BarEntry>()

        values.add(BarEntry(0.0f, 8.45f))
        values.add(BarEntry(1.0f, 6.56f))
        values.add(BarEntry(2.0f, 8.50f))
        values.add(BarEntry(3.0f, 7.45f))
        values.add(BarEntry(4.0f, 7.00f))
        values.add(BarEntry(5.0f, 9.10f))
        values.add(BarEntry(6.0f, 8.50f))

        val set1: BarDataSet

        if (chartBar.data != null && chartBar.data.dataSetCount > 0) {
            set1 = chartBar.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            chartBar.data.notifyDataChanged()
            chartBar.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, "Data Set")
            set1.setColors(*ColorTemplate.PASTEL_COLORS)
            set1.setDrawValues(false)

            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)

            val data = BarData(dataSets)
            chartBar.data = data
            chartBar.setFitBars(true)
        }

        //zoom
        chartBar.isDoubleTapToZoomEnabled = false
        chartBar.setPinchZoom(false)

        chartBar.setDrawGridBackground(false)
        chartBar.data.isHighlightEnabled = !chartBar.data.isHighlightEnabled

        chartBar.invalidate()

    }
}