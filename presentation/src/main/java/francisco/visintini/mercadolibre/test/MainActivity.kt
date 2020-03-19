package francisco.visintini.mercadolibre.test

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import francisco.visintini.mercadolibre.test.search.SearchViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main), HasAndroidInjector {

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModel: SearchViewModel

    override fun androidInjector() = supportFragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        window.decorView.setBackgroundColor(Color.WHITE)
        super.onCreate(savedInstanceState)
    }
}
