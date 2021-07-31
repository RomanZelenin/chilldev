package com.zelyder.chilldev

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class AppRecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<AppRecyclerAdapter.MyViewHolder>() {

    private val apps = ArrayList<String>()
    private lateinit var mListener: AdapterView.OnItemClickListener

    fun getItems() = apps

    fun getItem(position: Int) = apps[position]

    class MyViewHolder(
        itemView: View,
        private val listener: AdapterView.OnItemClickListener,
    ) : RecyclerView.ViewHolder(itemView) {
        private val appTextView: TextView = itemView.findViewById(R.id.app_text)

       fun bind(str : String, context : Context) {
           appTextView.text = str
       }
    }

    fun setData(items: List<String>) {
        apps.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.app_item, parent, false)
        return MyViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(apps[position], context)
    }

    override fun getItemCount() = apps.size
}