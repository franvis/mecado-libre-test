package francisco.visintini.mercadolibre.test.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import francisco.visintini.mercadolibre.data.di.DataModule
import francisco.visintini.mercadolibre.test.App
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.OkHttpClient

@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        DataModule::class
    ]
)
@Singleton
interface AppComponent {
    fun inject(app: App)

    fun okHttpClient(): OkHttpClient

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun baseUrl(@Named("BASE_URL") url: String): Builder

        fun build(): AppComponent
    }
}
