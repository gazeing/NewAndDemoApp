package com.willow.newreactdemoapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.willow.newreactdemoapp.R
import com.willow.newreactdemoapp.model.RecipeItem
import kotlinx.android.synthetic.main.viewholder_recipe.view.*

class RecipeListAdapter(val picasso: Picasso) :
    ListAdapter<RecipeItem, RecipeListAdapter.ItemViewholder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.viewholder_recipe, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecipeListAdapter.ItemViewholder, position: Int) {
        holder.bind(getItem(position), picasso)
    }

    class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: RecipeItem, picasso: Picasso) = with(itemView) {
            nameTextView.text = item.title
            picasso.load(item.thumbImage).into(itemImageView)
        }
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
