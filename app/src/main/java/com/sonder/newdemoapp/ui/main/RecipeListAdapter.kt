package com.sonder.newdemoapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sonder.newdemoapp.R
import com.sonder.newdemoapp.model.RecipeItem
import kotlinx.android.synthetic.main.viewholder_recipe.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class RecipeListAdapter :
    ListAdapter<RecipeItem, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.viewholder_recipe, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ItemViewHolder)?.bind(getItem(position))
    }


}

class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), KoinComponent {
     val picasso by inject<Picasso>()
    fun bind(item: RecipeItem) = with(itemView) {
        nameTextView.text = item.title
        picasso.load(item.thumbImage).into(itemImageView)
    }
}

class DiffCallback : DiffUtil.ItemCallback<RecipeItem>() {
    override fun areItemsTheSame(oldItem: RecipeItem, newItem: RecipeItem): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: RecipeItem, newItem: RecipeItem): Boolean {
        return oldItem == newItem
    }
}
