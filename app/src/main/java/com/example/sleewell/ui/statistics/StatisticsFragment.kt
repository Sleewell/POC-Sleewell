package com.example.sleewell.ui.statistics

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.DisplayMetrics
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sleewell.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_proto_activated.view.*
import java.util.ArrayList


class StatisticsFragment : Fragment() {

    private lateinit var staisticsViewModel: StatisticsViewModel
    private lateinit var chart : PieChart
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
        initGraphPie()
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

        setData(3, 100f)

        chart.animateY(1400, Easing.EaseInOutQuad)

        val l = chart.legend
        l.isEnabled = false
        /*l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 0f*/

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE)
        //chart.setEntryLabelTypeface(tfRegular)
        chart.setEntryLabelTextSize(12f)

        chart.invalidate()
    }

    private fun setData(count: Int, range: Float) {

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
}