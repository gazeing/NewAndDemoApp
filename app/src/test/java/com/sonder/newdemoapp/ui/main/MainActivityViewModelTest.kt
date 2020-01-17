package com.sonder.newdemoapp.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.sonder.newdemoapp.LiveDataTestUtil
import com.sonder.newdemoapp.MainCoroutineRule
import com.sonder.newdemoapp.data.RecipeRepo
import com.sonder.newdemoapp.model.RecipeItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainActivityViewModelTest {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var repo: RecipeRepo


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testList = listOf(
        RecipeItem(
            "testDes", listOf(), 1, listOf(), "url",
            "testTips", "testTitle"
        )
    )

    @Before
    fun setupRepo() {
        // We initialise the repository with no tasks
        repo = mock {
            onBlocking { getRecipeList() } doReturn testList

        }
    }


    @Test
    fun testViewModelWorks() = mainCoroutineRule.runBlockingTest {
        viewModel = MainActivityViewModel(repo)
        pauseDispatcher()
        viewModel.getRecipeList()
        assertNull(viewModel.recipeListLiveData.value)
        resumeDispatcher()
        assertNotNull(LiveDataTestUtil.getValue(viewModel.recipeListLiveData))
        assert(LiveDataTestUtil.getValue(viewModel.recipeListLiveData).getOrNull()?.size == 1)
        assert(LiveDataTestUtil.getValue(viewModel.recipeListLiveData).getOrNull()?.get(0) == testList[0])

    }


}
