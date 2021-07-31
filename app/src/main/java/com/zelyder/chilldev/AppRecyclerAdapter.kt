package com.zelyder.chilldev

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppRecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<AppRecyclerAdapter.MyViewHolder>() {

    private val apps = listOf("Кинопоиск HD", "youtube.com", "more.tv", "MEGOGO")
    private lateinit var mListener: AdapterView.OnItemClickListener

    class MyViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val appTextView: TextView = itemView.findViewById(R.id.app_text)

       fun bind(str : String, context : Context) {
           appTextView.text = str
       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.app_item, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(apps[position], context)
    }

    override fun getItemCount() = apps.size
}