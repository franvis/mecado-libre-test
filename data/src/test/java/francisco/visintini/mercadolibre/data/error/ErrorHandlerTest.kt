package francisco.visintini.mercadolibre.data.error

import com.squareup.moshi.JsonDataException
import francisco.visintini.mercadolibre.domain.error.ErrorEntity
import io.mockk.mockk
import java.io.IOException
import java.net.HttpURLConnection.HTTP_FORBIDDEN
import java.net.HttpURLConnection.HTTP_NOT_FOUND
import java.net.HttpURLConnection.HTTP_UNAVAILABLE
import org.amshove.kluent.shouldEqual
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class ErrorHandlerTest {

    private val errorHandlerImpl = ErrorHandlerImpl()

    @Test
    fun `Error handler mapError - with IOException - returns network error entity`() {
        errorHandlerImpl.mapError(IOException("mocked cause")) shouldEqual ErrorEntity.NetworkError
    }

    @Test
    fun `Error handler mapError - with Http not found exception - returns not found error entity`() {
        val response = Response.error<String>(HTTP_NOT_FOUND, mockk(relaxed = true))
        val exception = HttpException(response)
        errorHandlerImpl.mapError(exception) shouldEqual ErrorEntity.NotFound
    }

    @Test
    fun `Error handler mapError - with Http forbidden exception - returns not found error entity`() {
        val response = Response.error<String>(HTTP_FORBIDDEN, mockk(relaxed = true))
        val exception = HttpException(response)
        errorHandlerImpl.mapError(exception) shouldEqual ErrorEntity.AccessDenied
    }

    @Test
    fun `Error handler mapError - with unavailable exception - returns unavailable error entity`() {
        val response = Response.error<String>(HTTP_UNAVAILABLE, mockk(relaxed = true))
        val exception = HttpException(response)
        errorHandlerImpl.mapError(exception) shouldEqual ErrorEntity.ServiceUnavailable
    }

    @Test
    fun `Error handler mapError - with json parsing exception - returns parsing error entity`() {
        val exception = JsonDataException()
        errorHandlerImpl.mapError(exception) shouldEqual ErrorEntity.ParsingError
    }

    @Test
    fun `Error handler mapError - with unknown exception - returns unknown error entity`() {
        val exception = NotImplementedError()
        errorHandlerImpl.mapError(exception) shouldEqual ErrorEntity.UnknownError
    }
}
