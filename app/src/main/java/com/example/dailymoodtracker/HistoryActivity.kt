package com.example.dailymoodtracker

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class HistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val moodList = ArrayList<Mood>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        recyclerView = findViewById(R.id.recyclerViewMoods)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val url = "http://192.168.1.122:5001/api/history"
        val queue = Volley.newRequestQueue(this)

        val request = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                for (i in 0 until response.length()) {
                    val obj = response.getJSONObject(i)
                    moodList.add(
                        Mood(
                            obj.getString("mood"),
                            obj.getString("note"),
                            obj.getString("timestamp")
                        )
                    )
                }
                recyclerView.adapter = MoodAdapter(moodList)
            },
            { error ->
                Toast.makeText(this, "Hata: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        queue.add(request)
    }
}
