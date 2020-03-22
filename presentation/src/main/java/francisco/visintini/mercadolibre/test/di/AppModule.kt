package francisco.visintini.mercadolibre.test.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import francisco.visintini.mercadolibre.test.MainActivity
import javax.inject.Singleton
import kotlin.contracts.ExperimentalContracts

@Module
abstract class AppModule {

    @ExperimentalContracts
    @PerActivity
    @ContributesAndroidInjector(modules = [ActivityModule::class])
    abstract fun contributeMainActivityInjector(): MainActivity

    companion object {
        @Singleton
        @Provides
        fun provideContext(application: Application): Context = application
    }
}
