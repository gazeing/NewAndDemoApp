package com.willow.newreactdemoapp.ui.main

import androidx.lifecycle.*
import com.willow.newreactdemoapp.data.RecipeRepo
import com.willow.newreactdemoapp.di.SchedulerProvider
import com.willow.newreactdemoapp.model.RecipeItem
import com.willow.newreactdemoapp.switchMap
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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