package com.sonder.newdemoapp.data

import javax.inject.Inject

class RecipeRepo @Inject constructor(val api: RecipeAPI) {
    suspend fun getRecipeList() = api.getItems()
}