package com.zelyder.chilldev.ui.addapp

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zelyder.chilldev.customview.AppView
import com.zelyder.chilldev.extensions.dpToPx

class AppRecyclerAdapter(
    private val listener: AppClickListener
) : RecyclerView.Adapter<AppRecyclerAdapter.MyViewHolder>() {

    private val items = arrayListOf<AppItem>()

    class MyViewHolder(
        private val appView: AppView
    ) : RecyclerView.ViewHolder(appView) {

        fun bind(item: AppItem, listener: AppClickListener) {
            appView.text = item.name
            appView.setIcon(item.icon)
            appView.checked = item.checked
            appView.setOnClickListener {
                appView.checked = !appView.checked
                listener.onAppClick(item.name, appView.checked)
            }
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
        holder.bind(items[position], listener)
    }

    override fun getItemCount() = items.size


    fun setItems(items : List<AppItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}