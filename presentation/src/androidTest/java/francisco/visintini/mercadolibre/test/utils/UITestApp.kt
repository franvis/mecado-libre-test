package francisco.visintini.mercadolibre.test.utils

import francisco.visintini.mercadolibre.test.App
import francisco.visintini.mercadolibre.test.di.AppComponent
import francisco.visintini.mercadolibre.test.di.DaggerAppComponent

class UITestApp : App() {
    override fun buildAppComponent(): AppComponent =
        DaggerAppComponent
            .builder()
            .application(this)
            .baseUrl("http://localhost:8080/")
            .build()
}
