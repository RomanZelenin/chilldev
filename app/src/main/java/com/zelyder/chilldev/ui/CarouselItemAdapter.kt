package com.zelyder.chilldev.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.zelyder.chilldev.R
import com.zelyder.chilldev.ui.kidname.Item

class CarouselItemAdapter(
    val itemClick: (Int, Item) -> Unit
    ) : RecyclerView.Adapter<CarouselItemAdapter.ItemViewHolder>() {
    private var items: List<Item> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.kid_name_list_item, parent, false
            )
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            itemClick(position, items[position])
        }
    }

    override fun getItemCount() = items.size

    fun setItems(newItems: List<Item>) {
        items = newItems
        notifyDataSetChanged()
    }

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val listItem = view.findViewById<ImageView>(R.id.list_item_icon)
        fun bind(item: Item) {
            listItem.setImageResource(item.icon)
        }
    }
}