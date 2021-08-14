package com.zelyder.chilldev.ui.interests

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zelyder.chilldev.customview.InterestView
import com.zelyder.chilldev.domain.models.Category

class InterestsRecyclerAdapter(
    private val context: Context
) : RecyclerView.Adapter<InterestsRecyclerAdapter.MyViewHolder>() {

    private val interests = arrayListOf<String>()
    val checkedCategories = mutableListOf<Category>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = InterestView(context, null)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val view = holder.itemView as InterestView
        view.customOnClick = {
            if (view.checked) {
                checkedCategories.add(Category(position + 1, view.text))
            } else {
                checkedCategories.remove(Category(position + 1, view.text))
            }
        }
        holder.bind(interests[position])
    }

    override fun getItemCount(): Int {
        return interests.size
    }

    fun setItems(items: List<String>) {
        interests.clear()
        checkedCategories.clear()
        interests.addAll(items)
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