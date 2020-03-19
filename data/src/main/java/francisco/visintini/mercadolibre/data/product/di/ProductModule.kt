package francisco.visintini.mercadolibre.data.product.di

import dagger.Module
import dagger.Provides
import francisco.visintini.mercadolibre.data.di.RetrofitDummyWrapper
import francisco.visintini.mercadolibre.data.product.repository.ProductRepositoryImpl
import francisco.visintini.mercadolibre.data.product.service.ProductService
import francisco.visintini.mercadolibre.domain.repository.ErrorHandler
import francisco.visintini.mercadolibre.domain.repository.ProductRepository

@Module
internal class ProductModule {

    @Provides
    fun provideProductRepository(
        retrofitWrapper: RetrofitDummyWrapper,
        errorHandler: ErrorHandler
    ): ProductRepository {
        return ProductRepositoryImpl(
            retrofitWrapper.retrofit.create(ProductService::class.java),
            errorHandler
        )
    }
}
