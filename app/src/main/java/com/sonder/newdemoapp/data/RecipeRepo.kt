package com.sonder.newdemoapp.data

class RecipeRepo(val api: RecipeAPI) {
    suspend fun getRecipeList() = api.getItems()
}