package com.zelyder.chilldev.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.zelyder.chilldev.R
import com.zelyder.chilldev.customview.AccountView
import com.zelyder.chilldev.customview.AddProfileView
import com.zelyder.chilldev.domain.models.Account
import com.zelyder.chilldev.domain.models.KidNameIconType
import com.zelyder.chilldev.ui.chooseaccount.AccountClickListener
import com.zelyder.chilldev.ui.kidname.Item
import com.zelyder.chilldev.ui.kidname.KidNameFragment
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
//            LayoutInflater.from(parent.context).inflate(
//                R.layout.kid_name_list_item, parent, false
//            )
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
//            val listItem = view.root.findViewById<ImageView>(R.id.list_item_icon)
            view.icon.setImageResource(item.icon)
        }
    }
}