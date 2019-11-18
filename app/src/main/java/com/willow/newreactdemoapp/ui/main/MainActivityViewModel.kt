package com.willow.newreactdemoapp.ui.main

import androidx.lifecycle.*
import com.willow.newreactdemoapp.data.RecipeRepo
import com.willow.newreactdemoapp.model.RecipeItem
import com.willow.newreactdemoapp.switchMap
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel(val repo: RecipeRepo) : ViewModel() {

    private val requestLiveData = MutableLiveData<Int>()

    val recipeListLiveData: LiveData<List<RecipeItem>> =
        requestLiveData.switchMap {
            repo.getRecipeList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toFlowable().toLiveData()
        }

    fun getRecipeList() {
        requestLiveData.value = (requestLiveData.value ?: 0)
    }
}