package com.sonder.newdemoapp.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.sonder.newdemoapp.LiveDataTestUtil
import com.sonder.newdemoapp.MainCoroutineRule
import com.sonder.newdemoapp.data.RecipeAPI
import com.sonder.newdemoapp.data.RecipeRepo
import com.sonder.newdemoapp.di.DispatcherProvider
import com.sonder.newdemoapp.di.TestDispatcherProvider
import com.sonder.newdemoapp.di.uiModule
import com.sonder.newdemoapp.model.RecipeItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MainActivityViewModelTest {
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var repo: RecipeRepo
    private val dispatcherProvider: DispatcherProvider = TestDispatcherProvider()


    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val testList = listOf(
        RecipeItem(
            "testDes", listOf(), 1, listOf(), "url",
            "testTips", "testTitle"
        )
    )

    @Before
    fun setupViewModel() {
        // We initialise the repository with no tasks
        repo = mock {
            onBlocking { getRecipeList() } doReturn testList

        }

        // Create class under test

    }


    @Test
    fun testViewModelWorks() = mainCoroutineRule.runBlockingTest {
        viewModel = MainActivityViewModel(repo, dispatcherProvider)
        pauseDispatcher()
        viewModel.getRecipeList()
        assertNull(viewModel.recipeListLiveData.value)
        resumeDispatcher()
        assertNotNull(LiveDataTestUtil.getValue(viewModel.recipeListLiveData))
        assert(LiveDataTestUtil.getValue(viewModel.recipeListLiveData).size ==1)
        assert(LiveDataTestUtil.getValue(viewModel.recipeListLiveData)[0] == testList[0])

    }


}
