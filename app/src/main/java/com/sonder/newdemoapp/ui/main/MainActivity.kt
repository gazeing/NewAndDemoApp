package com.sonder.newdemoapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.sonder.newdemoapp.R
import kotlinx.android.synthetic.main.activity_main.*
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
            if (it.isSuccess) {
                (recyclerView.adapter as? RecipeListAdapter)?.submitList(it.getOrDefault(listOf()))
            } else {
                Snackbar.make(
                    recyclerView,
                    it.exceptionOrNull()?.message ?: "Something wrong",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })
        viewModel.getRecipeList()

        refreshButton.setOnClickListener { viewModel.getRecipeList() }
    }

}
