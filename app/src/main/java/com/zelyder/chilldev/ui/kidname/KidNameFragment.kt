package com.zelyder.chilldev.ui.kidname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.zelyder.chilldev.R
import com.zelyder.chilldev.databinding.KidNamePageBinding
import com.zelyder.chilldev.domain.models.KidNameIconType
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.kidNameEditText.text.apply {
            clear()
            append(viewModel.kidInfo.value!!.name)
        }
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
        binding.itemList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_IDLE ) {
                    (0 until binding.itemList.childCount).forEach { position ->
                        val item = binding.itemList.getChildAt(position)
                        if (item?.tag != null && item.tag as Boolean) {
                            viewModel.setIcon(KidNameIconType.getForPosition(position))
                        }
                    }
                }
            }
        })
        itemAdapter.setItems(iconItems)
        return binding.root
    }
}

data class Item(
    @DrawableRes val icon: Int
)