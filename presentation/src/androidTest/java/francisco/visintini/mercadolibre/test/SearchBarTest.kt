package francisco.visintini.mercadolibre.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasFocus
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchBarTest {
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun searchBar_searchBarEditTextClicked_showsBackArrowAndGainsFocus() {
        onView(withId(R.id.view_search_bar_edit_text)).perform(click())

        onView(withId(R.id.view_search_bar_back)).check(matches(isDisplayed()))
        onView(withId(R.id.view_search_bar_edit_text)).check(matches(hasFocus()))
    }

    @Test
    fun searchBar_searchBarEditTextClickedAndTextWritten_switchesFromSearchIconToCloseIcon() {
        // Given
        onView(withId(R.id.view_search_bar_image_search)).check(matches(isDisplayed()))

        // When
        onView(withId(R.id.view_search_bar_edit_text)).perform(click())
        onView(withId(R.id.view_search_bar_edit_text)).perform(ViewActions.typeText("test"))

        // Then
        onView(withId(R.id.view_search_bar_image_search)).check(matches(not(isDisplayed())))
        onView(withId(R.id.view_search_bar_image_clear)).check(matches(isDisplayed()))
    }

    @Test
    fun searchBar_searchBarEditTextWithTextBeingCleared_switchesFromCloseIconToSearchIcon() {
        // Given
        onView(withId(R.id.view_search_bar_edit_text)).perform(click())
        onView(withId(R.id.view_search_bar_edit_text)).perform(ViewActions.typeText("test"))

        // When
        onView(withId(R.id.view_search_bar_edit_text)).perform(ViewActions.clearText())

        // Then
        onView(withId(R.id.view_search_bar_image_search)).check(matches(isDisplayed()))
        onView(withId(R.id.view_search_bar_image_clear)).check(matches(not(isDisplayed())))
    }

    @Test
    fun searchBar_withKeyboardShownAndBackArrowTapped_editTextLosesFocusAndBackArrowIsHidden() {
        onView(withId(R.id.view_search_bar_edit_text)).perform(click())
        onView(withId(R.id.view_search_bar_edit_text)).perform(ViewActions.typeText("test"))
        onView(withId(R.id.view_search_bar_back)).perform(click())

        onView(withId(R.id.view_search_bar_back)).check(matches(not(isDisplayed())))
        onView(withId(R.id.view_search_bar_edit_text)).check(matches(not(hasFocus())))
    }
}
