package com.intermedia.challenge.ui.comics

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.intermedia.challenge.R
import com.intermedia.challenge.data.models.Comic
import com.intermedia.challenge.data.models.Event
import com.intermedia.challenge.databinding.ViewEventItemBinding
import com.intermedia.challenge.ui.base.BaseAdapter

class ComicEventsAdapter : BaseAdapter<Comic, ComicEventsAdapter.EventsViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder =
        EventsViewHolder(
            ViewEventItemBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.view_event_item,
                    parent,
                    false
                )
            )
        )

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class EventsViewHolder(
        private val binding: ViewEventItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Comic) = with(itemView) {
            val listOfEvents : List<Event> = listOf( //creating this because there is no Comic in the API with data in Events
                Event(name = "This is a Mocked Event #1 "),
                Event(name = "This is a Mocked Event #2 "),
                Event(name = "This is a Mocked Event #3 ")
            )
            binding.comic = item
            binding.rvEventInfo.adapter = ComicAppearencesAdapter(listOfEvents as MutableList<Event>)

            binding.arrowButtonDown.setOnClickListener {
                var showOrHide = binding.hiddenView.visibility
                if (showOrHide == View.GONE){
                    binding.hiddenView.visibility = View.VISIBLE
                }else{
                    binding.hiddenView.visibility = View.GONE
                }
            }
        }
    }
}