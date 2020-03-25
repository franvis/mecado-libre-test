package francisco.visintini.mercadolibre.data.search

import francisco.visintini.mercadolibre.data.search.dto.SearchDto
import francisco.visintini.mercadolibre.data.search.fixtures.SearchResultFixtures
import francisco.visintini.mercadolibre.data.search.repository.SearchRepositoryImpl
import francisco.visintini.mercadolibre.data.search.service.SearchService
import francisco.visintini.mercadolibre.domain.entity.Result
import francisco.visintini.mercadolibre.domain.error.ErrorHandler
import francisco.visintini.mercadolibre.domain.repository.SearchRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import java.lang.Exception
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class SearchRepositoryImplTest {

    private lateinit var service: SearchService
    private lateinit var errorHandler: ErrorHandler
    private lateinit var repository: SearchRepository

    @Before
    fun setup() {
        service = mockk()
        errorHandler = mockk(relaxed = true)
        repository = SearchRepositoryImpl(service, errorHandler)
    }

    /**
     * We have to use runBlocking and not runBlockingTest because of this open issue:
     * https://github.com/Kotlin/kotlinx.coroutines/issues/1204
     */
    @Test
    fun `Get search results - with success result - calls service and returns correct success result`() {
        runBlocking {
            // Given
            val mockedQuery = "mockedQuery"
            val mockedPage = 0
            val searchDto = mockk<SearchDto>()
            val searchResult = SearchResultFixtures.just()
            coEvery { service.search(any(), any()) } coAnswers { searchDto }
            every { searchDto.mapToDomain() } returns searchResult

            // When
            val result = repository.search(mockedQuery, mockedPage)

            // Then
            coVerify { service.search(mockedQuery, mockedPage) }
            result shouldBeInstanceOf Result.Success::class
            (result as Result.Success).result shouldEqual searchResult
        }
    }

    /**
     * We have to use runBlocking and not runBlockingTest because of this open issue:
     * https://github.com/Kotlin/kotlinx.coroutines/issues/1204
     */
    @ExperimentalCoroutinesApi
    @Test
    fun `Get search results - with error result - calls error handler and returns error result`() {
        runBlocking {
            // Given
            val mockedQuery = "mockedQuery"
            val mockedPage = 0
            val exception = Exception()
            coEvery { service.search(any(), any()) } throws exception

            // When
            val result = repository.search(mockedQuery, mockedPage)

            // Then
            coVerify {
                errorHandler.trackError(any())
                errorHandler.mapError(any())
            }
            result shouldBeInstanceOf Result.Error::class
        }
    }
}
