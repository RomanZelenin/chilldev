package com.zelyder.chilldev.ui.addapp

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zelyder.chilldev.customview.AppView
import com.zelyder.chilldev.extensions.dpToPx

class AppRecyclerAdapter :
    RecyclerView.Adapter<AppRecyclerAdapter.MyViewHolder>() {

    private val items = arrayListOf<AppItem>()

    class MyViewHolder(
        private val appView: AppView
    ) : RecyclerView.ViewHolder(appView) {

        fun bind(item: AppItem) {
            appView.text = item.name
            appView.color = ContextCompat.getColor(appView.context, item.color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = AppView(parent.context, null).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                70.dpToPx()
            )
        }
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size


    fun setItems(items : List<AppItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}