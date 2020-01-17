package com.sonder.newdemoapp.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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
                Toast.makeText(
                    this@MainActivity,
                    it.exceptionOrNull()?.message ?: "Something wrong",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        viewModel.getRecipeList()

        refreshButton.setOnClickListener { viewModel.getRecipeList() }
    }

}
