package com.zelyder.chilldev.ui.kidname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import com.zelyder.chilldev.R
import com.zelyder.chilldev.databinding.KidNamePageBinding
import com.zelyder.chilldev.ui.CarouselItemAdapter
import com.zelyder.chilldev.ui.FragmentPage

class KidNameFragment : FragmentPage<KidNamePageBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ) {
        _binding = KidNamePageBinding.inflate(inflater, container, attachToParent)
    }


    override fun onPause() {
        super.onPause()
        viewModel.setKidName(binding.kidNameEditText.text.toString())
    }

    companion object {
        @JvmStatic
        fun newInstance() = KidNameFragment()
    }

    private val itemAdapter by lazy {
        CarouselItemAdapter { position: Int, _: Item ->
            Toast.makeText(requireContext(), "Pos $position", Toast.LENGTH_LONG).show()
            binding.itemList.smoothScrollToPosition(position)
        }
    }
    private val iconItems = listOf(
        Item(R.drawable.ic_avas1),
        Item(R.drawable.ic_avas2),
        Item(R.drawable.ic_avas3),
        Item(R.drawable.ic_avas4),
        Item(R.drawable.ic_avas5),
        Item(R.drawable.ic_avas6),
        Item(R.drawable.ic_avas7),
        Item(R.drawable.ic_avas8),
        Item(R.drawable.ic_avas9),
        Item(R.drawable.ic_avas10)
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        inflater.inflate(R.layout.kid_name_page, container, false)

        binding.itemList.initialize(itemAdapter)
        binding.itemList.setViewsToChangeColor(listOf(R.id.list_item_background))
        itemAdapter.setItems(iconItems)

        return binding.root
    }

//    private fun getLargeListOfItems(): List<Item> {
//        val items = mutableListOf<Item>()
//        (0..40).map { items.add(iconItems.random()) }
//        return items
//    }
}

data class Item(
    @DrawableRes val icon: Int
)