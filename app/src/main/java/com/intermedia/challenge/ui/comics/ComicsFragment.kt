package com.intermedia.challenge.ui.comics

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.intermedia.challenge.data.models.Comic
import com.intermedia.challenge.databinding.FragmentEventsBinding
import com.intermedia.challenge.databinding.ViewEventItemBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ComicsFragment : Fragment() {

    private lateinit var binding: FragmentEventsBinding
    private val viewModel: ComicsViewModel by sharedViewModel()
    private val adapter = ComicEventsAdapter()
  /*  private val comicEventsAdapter = ComicAppearencesAdapter()
    private lateinit var eventBinding: ViewEventItemBinding*/


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventsBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEventsList()
        setupPagination()

    }

    private fun setupPagination() {
        binding.rvEventos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    viewModel.loadMoreEvents()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    private fun setupEventsList() {

        binding.rvEventos.adapter = adapter

        if (viewModel.errorExists()) {
            Toast.makeText(
                requireContext(),
                "Hubo un error cargando los eventos",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        viewModel.comicEvents.observe(viewLifecycleOwner, { comics ->

            var comicList: MutableList<Comic> = mutableListOf()
            for (item in comics) {
                val spanish :Locale =  Locale("es", "ES")
                val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                val outputFormat: DateFormat = SimpleDateFormat("EEEE, d MMMM yyyy", spanish)

                val fecha: String = item.dates[0].date

                val date: Date = inputFormat.parse(fecha)
                val outputDateStr: String = outputFormat.format(date)

                val comic = Comic(
                    id = item.id,
                    title = item.title,
                    thumbnail = item.thumbnail,
                    events = item.events,
                    dates = item.dates,
                    publishDate = outputDateStr
                )

                comicList.add(comic)
            }
            adapter.addAll(comicList, true)
        })
    }
}



