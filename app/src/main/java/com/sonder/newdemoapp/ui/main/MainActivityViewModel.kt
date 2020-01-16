package com.sonder.newdemoapp.ui.main

import androidx.lifecycle.*
import com.sonder.newdemoapp.data.RecipeRepo
import com.sonder.newdemoapp.di.SchedulerProvider
import com.sonder.newdemoapp.model.RecipeItem
import com.sonder.newdemoapp.switchMap
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainActivityViewModel(val repo: RecipeRepo) : ViewModel(),KoinComponent {

    val sp: SchedulerProvider by inject()

    private val requestLiveData = MutableLiveData<Int>()

    val recipeListLiveData: LiveData<List<RecipeItem>> =
        requestLiveData.switchMap {
            repo.getRecipeList().subscribeOn(sp.io())
                .observeOn(sp.ui())
                .toFlowable().toLiveData()
        }

    fun getRecipeList() {
        requestLiveData.value = (requestLiveData.value ?: 0)
    }
}