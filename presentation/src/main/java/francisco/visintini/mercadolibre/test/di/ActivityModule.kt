package francisco.visintini.mercadolibre.test.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import francisco.visintini.mercadolibre.test.messages.MessagesModule
import francisco.visintini.mercadolibre.test.product.ProductFragment
import francisco.visintini.mercadolibre.test.search.SearchFragment
import kotlin.contracts.ExperimentalContracts

@ExperimentalContracts
@Module
abstract class ActivityModule {
    @PerFragment
    @ContributesAndroidInjector(modules = [MessagesModule::class])
    abstract fun contributeSearchFragment(): SearchFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [MessagesModule::class])
    abstract fun contributeProductFragment(): ProductFragment
}
