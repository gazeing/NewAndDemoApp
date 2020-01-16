package com.sonder.newdemoapp

import androidx.test.core.app.ApplicationProvider
import com.sonder.newdemoapp.di.KoinTestApp
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Before
import java.io.File

open class BaseTest {
    lateinit var mockServer: MockWebServer

    val app: KoinTestApp = ApplicationProvider.getApplicationContext()

    @Before
    open fun setUp() {
        this.configureMockServer()
    }

    private fun configureMockServer() {
        mockServer = MockWebServer()
        mockServer.start()
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