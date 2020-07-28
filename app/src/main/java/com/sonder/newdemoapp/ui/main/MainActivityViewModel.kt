package com.sonder.newdemoapp.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.sonder.newdemoapp.data.RecipeRepo
import com.sonder.newdemoapp.model.RecipeItem
import com.sonder.newdemoapp.switchMap

class MainActivityViewModel @ViewModelInject constructor(val repo: RecipeRepo) : ViewModel() {

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