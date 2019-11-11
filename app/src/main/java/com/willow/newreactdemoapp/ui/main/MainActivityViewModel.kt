package com.willow.newreactdemoapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.willow.newreactdemoapp.data.RecipeRepo
import com.willow.newreactdemoapp.model.RecipeItem
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel(val repo: RecipeRepo) : ViewModel() {


    val recipeListLiveData: LiveData<List<RecipeItem>> = LiveDataReactiveStreams.fromPublisher(
        repo.getRecipeList().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).toFlowable()
    )
//        get() = MutableLiveData<List<RecipeItem>>()

//    fun getRecipeList() {
//        recipeListLiveData = LiveDataReactiveStreams.fromPublisher(
//            repo.getRecipeList().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).toFlowable()
//        )
//    }
}