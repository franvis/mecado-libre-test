package francisco.visintini.mercadolibre.data.di

import dagger.Module
import dagger.Provides
import francisco.visintini.mercadolibre.data.error.ErrorHandlerImpl
import francisco.visintini.mercadolibre.data.product.di.ProductModule
import francisco.visintini.mercadolibre.data.search.di.SearchModule
import francisco.visintini.mercadolibre.domain.error.ErrorHandler
import javax.inject.Named
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module(includes = [SearchModule::class, ProductModule::class])
class DataModule {

    @Provides
    fun providesOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    fun provideRetrofit(
        @Named("BASE_URL") apiBaseUrl: String,
        okHttpClient: OkHttpClient
    ): RetrofitDummyWrapper {
        return RetrofitDummyWrapper(
            Retrofit.Builder()
                .callFactory(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(apiBaseUrl)
                .build()
        )
    }

    @Provides
    fun providesErrorHandler(): ErrorHandler = ErrorHandlerImpl()
}

/**
 * This class is needed because of this a known dagger issue.
 * See: https://github.com/google/dagger/issues/970
 */
data class RetrofitDummyWrapper(val retrofit: Retrofit)
