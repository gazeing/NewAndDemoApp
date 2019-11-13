package com.willow.newreactdemoapp.ui.main

import androidx.lifecycle.*
import com.willow.newreactdemoapp.data.RecipeRepo
import com.willow.newreactdemoapp.model.RecipeItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel(val repo: RecipeRepo) : ViewModel() {

    private val _recipeListLiveData = MediatorLiveData<List<RecipeItem>>()

    val recipeListLiveData: LiveData<List<RecipeItem>>
        get() = _recipeListLiveData

    fun getRecipeList() {
        _recipeListLiveData.addSource(
            LiveDataReactiveStreams.fromPublisher(
                repo.getRecipeList().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .toFlowable()
            )
        ) {
            _recipeListLiveData.value = it
        }


    }
}