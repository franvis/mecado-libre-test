package francisco.visintini.mercadolibre.test.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import francisco.visintini.mercadolibre.test.search.SearchFragment

@Module
abstract class ActivityModule {
    @PerFragment
    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment
}
