package com.sonder.newdemoapp.ui.main

import androidx.test.core.app.ActivityScenario
import com.agoda.kakao.screen.Screen.Companion.onScreen
import com.squareup.picasso.Picasso
import com.sonder.newdemoapp.BaseTest
import com.sonder.newdemoapp.data.RecipeRepo
import com.sonder.newdemoapp.di.*
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@Config(application = KoinTestApp::class, sdk = [28])
@LooperMode(LooperMode.Mode.PAUSED)
class MainActivityTest : BaseTest() {

    private val modules = module {
        single { ApiProvider().createRecipeAPI(mockServer.url("/").toString()) }
        single { RecipeRepo(get()) }
        viewModel { MainActivityViewModel(get(), get()) }
        single { Picasso.Builder(app.applicationContext).build() }
        single { ApplicationDispatcherProvider() as DispatcherProvider }
    }

    @Test
    fun testServiceStatus_isFetched() {
        addDispatacher(
            requestContains = "receipes.json?",
            response = createMockHttpResponse("list_success.json", 200)
        )

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