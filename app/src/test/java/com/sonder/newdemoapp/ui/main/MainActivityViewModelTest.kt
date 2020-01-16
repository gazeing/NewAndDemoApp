package com.sonder.newdemoapp.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.sonder.newdemoapp.data.RecipeAPI
import com.sonder.newdemoapp.data.RecipeRepo
import com.sonder.newdemoapp.di.DispatcherProvider
import com.sonder.newdemoapp.di.TestDispatcherProvider
import com.sonder.newdemoapp.di.uiModule
import com.sonder.newdemoapp.model.RecipeItem
import org.junit.After
import org.junit.Assert.assertNotNull
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

class MainActivityViewModelTest : KoinTest {
    val viewModel: MainActivityViewModel by inject()
    val repo: RecipeRepo by inject()

    @Mock
    lateinit var uiData: Observer<List<RecipeItem>>


    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    val testCoroutineModule = module {
        // provided components
        single { TestDispatcherProvider() as DispatcherProvider }
    }

    val testList = listOf(
        RecipeItem(
            "testDes", listOf(), 1, listOf(), "url",
            "testTips", "testTitle"
        )
    )

    val mockApi: RecipeAPI = mock {
        onBlocking { getItems() } doReturn testList
    }


    val testDataModule = module {
        single { mockApi }
    }

    val testApp = listOf(testDataModule, uiModule, testCoroutineModule)


    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
        startKoin { modules(testApp) }
    }

    @After
    fun after() {
        stopKoin()
    }

    @Test
    fun testViewModelWorks() {


        viewModel.getRecipeList()

        viewModel.recipeListLiveData.observeForever(uiData)

        assertNotNull(viewModel.recipeListLiveData.value)

        Mockito.verify(uiData).onChanged(testList)
    }


}
