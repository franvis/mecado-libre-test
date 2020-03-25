package francisco.visintini.mercadolibre.test

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import francisco.visintini.mercadolibre.test.utils.MockServerDispatcher
import francisco.visintini.mercadolibre.test.utils.RecyclerViewMatcher
import francisco.visintini.mercadolibre.test.utils.waitViewShown
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchContentTest {
    @get:Rule
    var activityRule = ActivityTestRule(MainActivity::class.java)

    private lateinit var mockServer: MockWebServer

    @Before
    fun setUp() {
        mockServer = MockWebServer()
        mockServer.start(8080)
        mockServer.dispatcher = MockServerDispatcher()
    }

    @Test
    fun searchContent_searchedPerformed_showsPlaceholder() {
        // Given
        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.view_search_list)

        // When
        onView(withId(R.id.view_search_bar_edit_text)).perform(click())
        onView(withId(R.id.view_search_bar_edit_text)).perform(replaceText("placeholder"))
        onView(withId(R.id.view_search_bar_edit_text)).perform(pressImeActionButton())

        // Then
        waitViewShown(
            RecyclerViewMatcher(recyclerView.id)
                .atPositionOnView(0, R.id.view_search_result_image_placeholder)
        )
        onView(withId(R.id.view_search_bar_edit_text)).perform(clearText())
    }

    @Test
    fun searchContent_searchedPerformed_showsContent() {
        // Given
        val recyclerView = activityRule.activity.findViewById<RecyclerView>(R.id.view_search_list)
        // When
        onView(withId(R.id.view_search_bar_edit_text)).perform(click())
        onView(withId(R.id.view_search_bar_edit_text)).perform(replaceText("content"))
        onView(withId(R.id.view_search_bar_edit_text)).perform(pressImeActionButton())
        onView(withId(R.id.view_search_bar_edit_text)).perform(closeSoftKeyboard())

        // Then
        waitViewShown(
            RecyclerViewMatcher(recyclerView.id)
                .atPositionOnView(0, R.id.view_search_result_image)
        )
    }

    @Test
    fun searchContent_searchedPerformedWithEmptyResponse_showsEmptyView() {
        // When
        onView(withId(R.id.view_search_bar_edit_text)).perform(click())
        onView(withId(R.id.view_search_bar_edit_text)).perform(replaceText("emptyContent"))
        onView(withId(R.id.view_search_bar_edit_text)).perform(pressImeActionButton())

        // Then
        waitViewShown(withId(R.id.view_search_empty_result))
    }

    @After
    fun tearDown() {
        //  shut down
        mockServer.shutdown()
    }
}
