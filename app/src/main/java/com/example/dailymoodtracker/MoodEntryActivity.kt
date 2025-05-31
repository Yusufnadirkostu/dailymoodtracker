package com.example.dailymoodtracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MoodEntryActivity : AppCompatActivity() {

    private lateinit var selectedMood: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood_entry)

        val etNote = findViewById<EditText>(R.id.etNote)

        findViewById<Button>(R.id.btnHappy).setOnClickListener { selectedMood = "Happy" }
        findViewById<Button>(R.id.btnSad).setOnClickListener { selectedMood = "Sad" }
        findViewById<Button>(R.id.btnAngry).setOnClickListener { selectedMood = "Angry" }

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val note = etNote.text.toString()
            val timestamp = System.currentTimeMillis()

            val moodData = JSONObject().apply {
                put("mood", selectedMood)
                put("note", note)
                put("timestamp", timestamp)
            }

            val url = "http://192.168.1.122:5001/api/mood"
            val queue = Volley.newRequestQueue(this)
            val request = JsonObjectRequest(Request.Method.POST, url, moodData,
                { Toast.makeText(this, "Kaydedildi", Toast.LENGTH_SHORT).show() },
                { error -> Toast.makeText(this, "Hata: ${error.message}", Toast.LENGTH_SHORT).show() })
            queue.add(request)
        }
    }
}
