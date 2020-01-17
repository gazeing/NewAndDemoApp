package com.sonder.newdemoapp.ui.main

import androidx.lifecycle.*
import com.sonder.newdemoapp.data.RecipeRepo
import com.sonder.newdemoapp.di.DispatcherProvider
import com.sonder.newdemoapp.model.RecipeItem
import com.sonder.newdemoapp.switchMap
import org.koin.core.KoinComponent

class MainActivityViewModel(val repo: RecipeRepo, val dp: DispatcherProvider) : ViewModel(),
    KoinComponent {

    private val requestLiveData = MutableLiveData<Int>()


    val recipeListLiveData: LiveData<Result<List<RecipeItem>>> =
        requestLiveData.switchMap {
            liveData(context = viewModelScope.coroutineContext) {
                try {
                    val data = repo.getRecipeList()
                    emit(Result.success(data))
                } catch (e: Exception) {
                    emit(Result.failure(e))
                }

            }

        }

    fun getRecipeList() {
        requestLiveData.value = (requestLiveData.value ?: 0)
    }
}