package com.sonder.newdemoapp.data

class RecipeRepo(val api: RecipeAPI) {
    fun getRecipeList() = api.getItems()
}