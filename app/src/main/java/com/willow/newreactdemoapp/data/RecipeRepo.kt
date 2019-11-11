package com.willow.newreactdemoapp.data

class RecipeRepo(val api: RecipeAPI) {
    fun getRecipeList() = api.getItems()
}