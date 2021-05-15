package com.intermedia.challenge.ui.charaterDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.intermedia.challenge.R
import com.intermedia.challenge.data.models.Appearance
import com.intermedia.challenge.databinding.ComicAppearenceRowItemBinding
import com.intermedia.challenge.ui.base.BaseAdapter

class CharactersDetailAdapter : BaseAdapter<Appearance, CharactersDetailAdapter.CharactersDetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersDetailViewHolder =
        CharactersDetailViewHolder(
            ComicAppearenceRowItemBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.comic_appearence_row_item,
                    parent,
                    false
                )
            )
        )

    override fun onBindViewHolder(holder: CharactersDetailViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class CharactersDetailViewHolder(
        private val binding: ComicAppearenceRowItemBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Appearance) {
            binding.tComicAppeareance.text = item.name
        }
    }
}