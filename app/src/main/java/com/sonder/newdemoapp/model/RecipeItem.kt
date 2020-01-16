package com.sonder.newdemoapp.model

data class RecipeItem(
    val description: String,
    val material: List<Material>,
    val receipeOrder: Int,
    val steps: List<Step>,
    val thumbImage: String,
    val tips: String,
    val title: String
)

data class Material(
    val measureWord: String,
    val name: String,
    val quantity: Int
)

data class Step(
    val description: String,
    val image: String,
    val order: Int
)