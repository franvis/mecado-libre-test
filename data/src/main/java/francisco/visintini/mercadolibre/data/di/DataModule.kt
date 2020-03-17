package francisco.visintini.mercadolibre.data.di

import dagger.Module
import dagger.Provides
import francisco.visintini.mercadolibre.data.error.ErrorHandlerImpl
import francisco.visintini.mercadolibre.data.product.di.ProductModule
import francisco.visintini.mercadolibre.data.search.di.SearchModule
import francisco.visintini.mercadolibre.domain.repository.ErrorHandler
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module(includes = [SearchModule::class, ProductModule::class])
class DataModule {

    @Provides
    fun provideRetrofit(): RetrofitDummyWrapper {
        return RetrofitDummyWrapper(
            Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(API_BASE_URL)
                .build()
        )
    }

    @Provides
    fun providesErrorHandler(): ErrorHandler = ErrorHandlerImpl()

    companion object {
        private const val API_BASE_URL = "https://api.mercadolibre.com"
    }
}

/**
 * This class is needed because of this a known dagger issue.
 * See: https://github.com/google/dagger/issues/970
 */
data class RetrofitDummyWrapper(val retrofit: Retrofit)
