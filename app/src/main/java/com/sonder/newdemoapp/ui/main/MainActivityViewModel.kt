package com.sonder.newdemoapp.ui.main

import androidx.lifecycle.*
import com.sonder.newdemoapp.data.RecipeRepo
import com.sonder.newdemoapp.di.DispatcherProvider
import com.sonder.newdemoapp.model.RecipeItem
import com.sonder.newdemoapp.switchMap
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainActivityViewModel(val repo: RecipeRepo,  val dp: DispatcherProvider) : ViewModel(),KoinComponent {


    private val requestLiveData = MutableLiveData<Int>()

    val recipeListLiveData: LiveData<List<RecipeItem>> =
        requestLiveData.switchMap {
            liveData(context = viewModelScope.coroutineContext) {
                emit(repo.getRecipeList())
            }

        }

    fun getRecipeList() {
        requestLiveData.value = (requestLiveData.value ?: 0)
    }
}