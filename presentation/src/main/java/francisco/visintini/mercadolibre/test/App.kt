package francisco.visintini.mercadolibre.test

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import francisco.visintini.mercadolibre.test.di.AppComponent
import francisco.visintini.mercadolibre.test.di.DaggerAppComponent
import javax.inject.Inject

open class App : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    open lateinit var appComponent: AppComponent

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        appComponent = buildAppComponent()
        appComponent.inject(this)
    }

    open fun buildAppComponent(): AppComponent = DaggerAppComponent
        .builder()
        .application(this)
        .baseUrl(BuildConfig.API_BASE_URL)
        .build()
}
