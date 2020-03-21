package francisco.visintini.mercadolibre.test.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import francisco.visintini.mercadolibre.test.product.ProductFragment
import francisco.visintini.mercadolibre.test.search.SearchFragment

@Module
abstract class ActivityModule {
    @PerFragment
    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun contributeProductFragment(): ProductFragment
}
