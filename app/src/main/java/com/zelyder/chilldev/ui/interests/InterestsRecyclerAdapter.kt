package com.zelyder.chilldev.ui.interests

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zelyder.chilldev.customview.InterestView

class InterestsRecyclerAdapter(
    private val context: Context
) : RecyclerView.Adapter<InterestsRecyclerAdapter.MyViewHolder>() {

    private val interests = arrayListOf<String>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = InterestView(context, null)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(interests[position])
    }

    override fun getItemCount(): Int {
        return interests.size
    }

    fun setItems(items: List<String>) {
        interests.clear()
        interests.addAll(items)
        notifyDataSetChanged()
    }

    class MyViewHolder(
        itemView: InterestView
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(str: String) {
            val view = itemView as InterestView
            view.text = str
            view.checked = false
        }
    }
}