package com.sonder.newdemoapp.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sonder.newdemoapp.data.RecipeRepo
import com.sonder.newdemoapp.di.*
import com.sonder.newdemoapp.model.RecipeItem
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
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
import java.io.File

class MainActivityViewModelTest : KoinTest {
    val viewModel: MainActivityViewModel by inject()
    val repo: RecipeRepo by inject()

    lateinit var mockServer: MockWebServer

    @Mock
    lateinit var uiData: Observer<List<RecipeItem>>


    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    val testRxModule = module {
        // provided components
        single { TestSchedulerProvider() as SchedulerProvider }
    }

    val testDataModule = module {
        single { ApiProvider().createRecipeAPI(mockServer.url("/").toString()) }
    }

    val testApp = listOf(testDataModule, uiModule , testRxModule)


    @Before
    fun before() {
        mockServer = MockWebServer()
        mockServer.start()
        MockitoAnnotations.initMocks(this)
        startKoin { modules( testApp) }

        addDispatacher(
            requestContains = "receipes.json?",
            response = createMockHttpResponse("list_success.json", 200)
        )
    }

    @After
    fun after() {
        stopKoin()
        mockServer.shutdown()
    }

    @Test
    fun testViewModelWorks() {

        val list = repo.getRecipeList().blockingGet()

        viewModel.getRecipeList()

        viewModel.recipeListLiveData.observeForever(uiData)

        assertNotNull(viewModel.recipeListLiveData.value)

        Mockito.verify(uiData).onChanged(list)
    }

    open fun enqueueMockHttpResponse(fileName: String, responseCode: Int) = mockServer.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJson(fileName))
    )

    fun createMockHttpResponse(fileName: String, responseCode: Int) = MockResponse()
        .setResponseCode(responseCode)
        .setBody(getJson(fileName))

    fun addDispatacher(requestContains: String, response: MockResponse) {
        mockServer.setDispatcher(object : Dispatcher() {
            override fun dispatch(request: RecordedRequest?): MockResponse {
                return if (request?.path?.contains(requestContains, true) == true) {
                    response
                } else
                    MockResponse().setResponseCode(404)
            }
        })
    }

    private fun getJson(path: String): String {
        val uri = this.javaClass.classLoader?.getResource(path)
        uri?.let {
            val file = File(it.path)
            return String(file.readBytes())
        }
        return ""
    }

}
