package com.zelyder.chilldev

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AddAppScreenFragment : Fragment() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter: AppRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_page_6, container, false)

        recyclerView = view.findViewById(R.id.recycler)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        adapter = AppRecyclerAdapter(view.context)

        val appList : List<String> = listOf("Кинопоиск HD", "youtube.com", "more.tv", "MEGOGO")
        adapter.setData(appList)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}