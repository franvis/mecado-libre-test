package francisco.visintini.mercadolibre.data.search.di

import dagger.Module
import dagger.Provides
import francisco.visintini.mercadolibre.data.di.RetrofitDummyWrapper
import francisco.visintini.mercadolibre.data.search.repository.SearchRepositoryImpl
import francisco.visintini.mercadolibre.data.search.service.SearchService
import francisco.visintini.mercadolibre.domain.repository.ErrorHandler
import francisco.visintini.mercadolibre.domain.repository.SearchRepository

@Module
internal class SearchModule {

    @Provides
    fun provideSearchRepository(
        retrofitWrapper: RetrofitDummyWrapper,
        errorHandler: ErrorHandler
    ): SearchRepository {
        return SearchRepositoryImpl(
            retrofitWrapper.retrofit.create(SearchService::class.java),
            errorHandler
        )
    }
}