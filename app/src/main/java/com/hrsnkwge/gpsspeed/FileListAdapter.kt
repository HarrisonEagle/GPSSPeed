package com.hrsnkwge.gpsspeed

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.util.*

class FileListAdapter(private val items: ArrayList<String?>,private val activity: Activity) : RecyclerView.Adapter<FileListAdapter.ViewHolder>() {
    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false) as TextView
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items.get(position)
        holder.textView.setTextColor(Color.parseColor("#FFFFFF"))
        holder.textView.setOnClickListener {
            val intent = Intent(activity,GraphActivity::class.java)
            intent.putExtra("filename",holder.textView.text)
            activity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}