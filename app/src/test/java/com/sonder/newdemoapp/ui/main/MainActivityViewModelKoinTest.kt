package com.sonder.newdemoapp.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.sonder.newdemoapp.LiveDataTestUtil
import com.sonder.newdemoapp.MainCoroutineRule
import com.sonder.newdemoapp.data.RecipeRepo
import com.sonder.newdemoapp.di.coroutineModule
import com.sonder.newdemoapp.di.uiModule
import com.sonder.newdemoapp.model.RecipeItem
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject
import org.mockito.MockitoAnnotations
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainActivityViewModelKoinTest : KoinTest {
    val viewModel: MainActivityViewModel by inject()

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

    val testRepo = module {
        single<RecipeRepo> {
            mock {
                onBlocking { getRecipeList() } doReturn testList
            }
        }
        viewModel { MainActivityViewModel(get()) }
    }

    val testApp = listOf(testRepo, coroutineModule)

    @Before
    fun setupRepo() {
        MockitoAnnotations.initMocks(this)
        startKoin { modules(testApp) }
    }

    @After
    fun after() {
        stopKoin()
    }


    @Test
    fun testViewModelWorks() = mainCoroutineRule.runBlockingTest {
        pauseDispatcher()
        viewModel.getRecipeList()
        assertNull(viewModel.recipeListLiveData.value)
        resumeDispatcher()
        assertNotNull(LiveDataTestUtil.getValue(viewModel.recipeListLiveData))
        assert(LiveDataTestUtil.getValue(viewModel.recipeListLiveData).getOrNull()?.size == 1)
        assert(LiveDataTestUtil.getValue(viewModel.recipeListLiveData).getOrNull()?.get(0) == testList[0])

    }


}
