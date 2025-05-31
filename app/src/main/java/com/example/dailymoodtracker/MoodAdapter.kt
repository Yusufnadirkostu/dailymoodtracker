package com.example.dailymoodtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MoodAdapter(private val moods: List<Mood>) : RecyclerView.Adapter<MoodAdapter.MoodViewHolder>() {

    class MoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMood: TextView = itemView.findViewById(R.id.tvMood)
        val tvNote: TextView = itemView.findViewById(R.id.tvNote)
        val tvTimestamp: TextView = itemView.findViewById(R.id.tvTimestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mood, parent, false)
        return MoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {
        val mood = moods[position]
        holder.tvMood.text = "Mood: ${mood.mood}"
        holder.tvNote.text = "Note: ${mood.note}"
        holder.tvTimestamp.text = "Date: ${mood.timestamp}"
    }

    override fun getItemCount(): Int = moods.size
}
