package francisco.visintini.mercadolibre.test.utils

import java.util.concurrent.TimeUnit
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import okio.Buffer

class MockServerDispatcher : Dispatcher() {
    override fun dispatch(request: RecordedRequest): MockResponse {
        return when (request.path) {
            PATH.format("content") -> {
                MockResponse()
                    .setResponseCode(200)
                    .setBody(getJsonBody("search_result.json") ?: Buffer())
            }
            PATH.format("placeholder") -> MockResponse()
                .setBodyDelay(300, TimeUnit.MILLISECONDS)
                .setResponseCode(200)
                .setBody(getJsonBody("search_result.json") ?: Buffer())
            PATH.format("emptyContent") -> MockResponse()
                .setResponseCode(200)
                .setBody(getJsonBody("search_result_empty.json") ?: Buffer())
            PATH.format("networkError") -> MockResponse()
                .setBodyDelay(1000, TimeUnit.MILLISECONDS) // This causes an IOException
                .setResponseCode(200)
            PATH.format("unknownError") -> MockResponse()
                .setResponseCode(500)
            else -> MockResponse().setResponseCode(400)
        }
    }

    private fun getJsonBody(fileName: String): Buffer? =
        javaClass.classLoader?.let { Buffer().readFrom(it.getResourceAsStream(fileName)) }

    companion object {
        const val PATH = "/sites/MLA/search?q=%s&offset=0"
    }
}
