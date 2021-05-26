package com.intermedia.challenge.ui.comics


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.intermedia.challenge.R
import com.intermedia.challenge.data.models.Comic
import com.intermedia.challenge.data.models.Event
import com.intermedia.challenge.databinding.ComicAppearenceRowItemBinding
import com.intermedia.challenge.databinding.EventsAppearenceRowItemBinding
import com.intermedia.challenge.databinding.ViewEventItemBinding
import com.intermedia.challenge.ui.base.BaseAdapter

class ComicAppearencesAdapter(eventList: MutableList<Event>) : RecyclerView.Adapter<ComicAppearencesAdapter.MyViewHolder>() {


    private var list : MutableList<Event> = eventList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.events_appearence_row_item, parent , false)
        return MyViewHolder(vista)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = list[position].name
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name : TextView = itemView.findViewById(R.id.t_comic_appeareance)


    }

}