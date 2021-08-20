package com.zelyder.chilldev.ui.kidname

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
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

    override fun onResume() {
        super.onResume()
        requestFocusOnKeyboard()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.kidNameText.apply {
            text = viewModel.kidInfo.value!!.name
        }
        binding.itemList.layoutManager!!.scrollToPosition(Integer.MAX_VALUE / 2)
    }

    override fun onPause() {
        super.onPause()
        viewModel.setKidName(binding.kidNameText.text.toString())
    }

    companion object {
        @JvmStatic
        fun newInstance() = KidNameFragment()
    }

    private val itemAdapter by lazy {
        CarouselItemAdapter(requireContext()) { position: Int, _: Item ->
            binding.itemList.smoothScrollToPosition(position)
            binding.keyboardView.requestFocus()
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
    private var keyboardView: KeyboardView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        inflater.inflate(R.layout.kid_name_page, container, false)
        viewModel.setIcon(KidNameIconType.getForPosition(1))

        keyboardView = binding.keyboardView
        binding.itemList.initialize(itemAdapter)
        binding.itemList.requestFocus()
        binding.itemList.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.HORIZONTAL,
            false
        )
        binding.itemList.setOnFocusChangeListener { focused, direction ->
            focused.requestFocus()
        }

        itemAdapter.setItems(iconItems)

        keyboardView?.apply {
            setInputXml(resources.getXml(R.xml.input))
            bindInput(KeyboardListenerWrapper(object : KeyboardView.KeyboardListener {
                override fun onInput(symbol: Char?) {
                    val textView = binding.kidNameText
                    textView.text = (textView.text.toString() + symbol.toString())
                    textView.setTextColor(ContextCompat.getColor(context, R.color.white))
                }

                override fun onDelete() {
                    val textView = binding.kidNameText
                    if (textView.text.isNotEmpty()) {
                        textView.text = textView.text.substring(0, textView.text.length - 1)
                    }
                }

                override fun onDeleteAll() {
                }

                override fun onEnter() {
                    val textView = binding.kidNameText
                    textView.text = textView.text.toString().replace("\\s+".toRegex(), " ")
                    if (textView.text.isEmpty()) {
                        textView.text = resources.getText(R.string.saved_kid_name)
                    }
                    page.swipeToNext()
                }
            }))
            setKeyboardNextFocusListener(keyboardNextFocusListener)
        }

        binding.itemList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_IDLE) {
                    val position =
                        binding.itemList.getChildAdapterPosition(binding.itemList.focusedChild) + 1
                    viewModel.setIcon(KidNameIconType.getForPosition(position))
                }
            }
        })
        return binding.root
    }

    override fun onDestroyView() {
        keyboardView?.unbindInput()
        keyboardView?.setKeyboardNextFocusListener(null)
        keyboardView = null
        super.onDestroyView()
    }

    private inner class KeyboardListenerWrapper(private val listener: KeyboardView.KeyboardListener) : KeyboardView.KeyboardListener {
        override fun onInput(symbol: Char?) {
            listener.onInput(symbol)
        }

        override fun onEnter() {
            listener.onEnter()
        }

        override fun onDelete() {
            listener.onDelete()
        }

        override fun onDeleteAll() {
            listener.onDeleteAll()
        }
    }

    private val keyboardNextFocusListener = object : SearchNextFocusListener {
        override fun searchDown(focused: View?): View? {
            focused?.requestFocus()
            return null
        }

        override fun searchTop(focused: View?): View? {
            focused?.requestFocus()
            return null
        }
    }

    private fun requestFocusOnKeyboard() {
        binding.keyboardView.apply {
            requestFocus()
            sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED)
        }
    }
}

data class Item(
    @DrawableRes val icon: Int
)