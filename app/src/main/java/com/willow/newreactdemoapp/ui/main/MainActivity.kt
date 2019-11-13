package com.willow.newreactdemoapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.willow.newreactdemoapp.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    val viewModel by viewModel<MainActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.adapter = RecipeListAdapter()
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        viewModel.recipeListLiveData.observe(this, Observer {
            (recyclerView.adapter as? RecipeListAdapter)?.submitList(it)
        })
        viewModel.getRecipeList()

        refreshButton.setOnClickListener { viewModel.getRecipeList() }
    }

}
