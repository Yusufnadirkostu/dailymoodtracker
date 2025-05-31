package com.example.dailymoodtracker

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import org.json.JSONObject

class SummaryActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart
    private lateinit var tvMostFrequent: TextView
    private val moodCounts = mutableMapOf<String, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary)

        barChart = findViewById(R.id.barChart)
        tvMostFrequent = findViewById(R.id.tvMostFrequentMood)

        fetchMoodSummary()
    }

    private fun fetchMoodSummary() {
        val url = "http://192.168.1.122:5001/api/summary"
        val requestQueue = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val moods = response.getJSONObject("moods")
                val entries = ArrayList<BarEntry>()
                var index = 0
                for (key in moods.keys()) {
                    val count = moods.getInt(key)
                    entries.add(BarEntry(index++.toFloat(), count.toFloat()))
                    moodCounts[key] = count
                }

                val dataSet = BarDataSet(entries, "7 Günlük Mood")
                barChart.data = BarData(dataSet)
                barChart.invalidate()

                val mostFrequent = moodCounts.maxByOrNull { it.value }?.key
                tvMostFrequent.text = "En sık hissedilen: $mostFrequent"
            },
            { error ->
                Toast.makeText(this, "Hata: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        requestQueue.add(request)
    }
}
