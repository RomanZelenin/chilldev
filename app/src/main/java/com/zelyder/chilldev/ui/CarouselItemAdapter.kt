package com.zelyder.chilldev.ui

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zelyder.chilldev.ui.kidname.Item
import com.zelyder.chilldev.ui.kidname.KidNameView
import java.lang.Integer.MAX_VALUE

class CarouselItemAdapter(
    val context: Context,
    val itemClick: (Int, Item) -> Unit
) : RecyclerView.Adapter<CarouselItemAdapter.ItemViewHolder>() {
    private var items: List<Item> = listOf()

    override fun getItemCount(): Int {
        return MAX_VALUE;
    }

    fun setItems(newItems: List<Item>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            KidNameView(context, null)
        )

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val positionInList: Int = position % items.size
        holder.bind(items[positionInList])
        holder.itemView.setOnClickListener {
            itemClick(positionInList, items[positionInList])
        }
    }

    class ItemViewHolder(private val view: KidNameView) : RecyclerView.ViewHolder(view) {
        fun bind(item: Item) {
            view.icon.setImageResource(item.icon)
        }
    }
}