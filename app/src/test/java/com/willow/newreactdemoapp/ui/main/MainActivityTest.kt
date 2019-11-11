package com.willow.newreactdemoapp.ui.main

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.squareup.picasso.Picasso
import com.willow.newreactdemoapp.data.RecipeRepo
import com.willow.newreactdemoapp.di.ApiProvider
import com.willow.newreactdemoapp.di.KoinTestApp
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File

@RunWith(RobolectricTestRunner::class)
@Config(application = KoinTestApp::class, sdk = [28])
class MainActivityTest {

    val app: KoinTestApp = ApplicationProvider.getApplicationContext()
    lateinit var mockServer: MockWebServer

    @Before
    open fun setUp() {
        this.configureMockServer()
    }

    private fun configureMockServer() {
        mockServer = MockWebServer()
        mockServer.start()
    }

    open fun mockHttpResponse(fileName: String, responseCode: Int) = mockServer.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJson(fileName))
    )

    private fun getJson(path: String): String {
        val uri = this.javaClass.classLoader?.getResource(path)
        uri?.let {
            val file = File(it.path)
            return String(file.readBytes())
        }
        return ""
    }

    @Test
    fun testServiceStatus_isFetched() {
        val modules = module {
            single { ApiProvider().createRecipeAPI(mockServer.url("/").toString()) }
            single { RecipeRepo(get()) }
            viewModel { MainActivityViewModel(get()) }
            single { Picasso.Builder(app.applicationContext).build() }
        }

        mockHttpResponse("list_success.json", 200)

        app.loadModules(modules) {
            // Start mocking from here
            ActivityScenario.launch(MainActivity::class.java)
            onScreen<MainScreen> {
                recycler {
                    firstChild<Item> {
                        isVisible()
                        title { hasText("关东煮") }
                    }
                }
            }
        }
    }
}