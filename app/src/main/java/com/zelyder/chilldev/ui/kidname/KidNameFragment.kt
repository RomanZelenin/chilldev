package com.zelyder.chilldev.ui.kidname

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.zelyder.chilldev.R
import com.zelyder.chilldev.databinding.KidNamePageBinding
import com.zelyder.chilldev.domain.models.KidNameIconType
import com.zelyder.chilldev.ui.CarouselItemAdapter
import com.zelyder.chilldev.ui.FragmentPage
import com.zelyder.chilldev.ui.chooseaccount.AccountsLinearLayoutManager
import com.zelyder.chilldev.ui.movieage.AgeRatingLayout
import java.lang.Exception


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
        binding.kidNameText.apply {
            setText(viewModel.kidInfo.value!!.name)
        }
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
            binding.itemList.scrollToPosition(position)
            binding.kidNameText.requestFocus()
            (requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
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

//        keyboardView = binding.keyboardView
        binding.itemList.initialize(itemAdapter)
        binding.itemList.requestFocus()
        binding.itemList.layoutManager = AccountsLinearLayoutManager(requireContext())
        binding.itemList.requestFocus()


        binding.itemList.setOnFocusChangeListener { focused, direction ->
            Log.wtf("helloy", "$direction, $focused")
            focused.requestFocus()
        }
//        val icon = AppCompatResources.getDrawable(requireContext(), R.drawable.circle_item)
//        binding.keyboardView.setKeySelector(icon)
        itemAdapter.setItems(iconItems)


//        keyboardView?.apply {
//            setInputXml(resources.getXml(R.xml.input))
//            bindInput(KeyboardListenerWrapper(object : KeyboardView.KeyboardListener {
//                override fun onInput(symbol: Char?) {
//                    val textView = binding.kidNameText
//                    textView.append(symbol.toString())
//                }
//
//                override fun onDelete() {
//                    val textView = binding.kidNameText
//                    if (textView.text.isNotEmpty()) {
//                        textView.text = textView.text.substring(0, textView.text.length - 1)
//                    }
//                }
//
//                override fun onDeleteAll() {
//                    Log.wtf("button", "deleteAll")
//
//                }
//
//                override fun onEnter() {
//                    val textView = binding.kidNameText
//                    textView.text = textView.text.toString().replace("\\s+".toRegex(), " ")
//                    if (textView.text.isNotEmpty()) {
////                        textView.text = "21"
//                    }
//                }
//            }))
//            setKeyboardNextFocusListener(keyboardNextFocusListener)
//        }

//        val kidNameEditText = binding.kidNameText

        binding.kidNameText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.kidNameText.clearFocus()
                try {
                    val imm: InputMethodManager? =
                        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                    imm?.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
                } catch (e: Exception) {
                    Log.e(this::class.simpleName, "Hide keyboard failed")
                }
                page.swipeToNext()
            }
            false
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

//        binding.itemList.setOnKeyListener { v, keyCode, event ->
//            if (event.action == KeyEvent.ACTION_DOWN) {
//                when (keyCode) {
//                    KeyEvent.KEYCODE_DPAD_CENTER -> {
//                        Handler(Looper.getMainLooper()).post {
//                            binding.kidNameText.requestFocus()
//                        }
//                        true
//                    }
//                    else -> false
//                }
//            } else {
//                false
//            }
//        }

//        binding.itemList.layoutManager?.scrollToPosition(5)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        keyboardView?.unbindInput()
        keyboardView?.setKeyboardNextFocusListener(null)
        keyboardView = null
        super.onDestroyView()
    }

    private inner class KeyboardListenerWrapper(private val listener: KeyboardView.KeyboardListener) :
        KeyboardView.KeyboardListener {
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
            return null
        }

        override fun searchTop(focused: View?): View? {
            return null
        }
    }
}


data class Item(
    @DrawableRes val icon: Int
)