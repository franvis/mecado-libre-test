package francisco.visintini.mercadolibre.test.utils

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.ViewFinder
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import java.lang.reflect.Field
import org.hamcrest.Matcher

class ViewShownIdlingResource(private val viewMatcher: Matcher<View?>?) : IdlingResource {
    private var resourceCallback: IdlingResource.ResourceCallback? = null
    override fun isIdleNow(): Boolean {
        val view: View? = getView(viewMatcher)
        val idle = view == null || view.isShown
        if (idle && resourceCallback != null) {
            resourceCallback!!.onTransitionToIdle()
        }
        return idle
    }

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback?) {
        this.resourceCallback = resourceCallback
    }

    override fun getName(): String? {
        return this.toString() + viewMatcher.toString()
    }

    companion object {
        private val TAG: String? = ViewShownIdlingResource::class.java.simpleName
        private fun getView(viewMatcher: Matcher<View?>?): View? {
            return try {
                val viewInteraction = onView(viewMatcher)
                val finderField: Field = viewInteraction!!.javaClass.getDeclaredField("viewFinder")
                finderField.isAccessible = true
                val finder = finderField.get(viewInteraction) as ViewFinder
                finder.view
            } catch (e: Exception) {
                null
            }
        }
    }
}

fun waitViewShown(matcher: Matcher<View?>?) {
    val idlingResource: IdlingResource = ViewShownIdlingResource(matcher) // /
    try {
        IdlingRegistry.getInstance().register(idlingResource)
        onView(matcher).check(matches(isDisplayed()))
    } finally {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}
