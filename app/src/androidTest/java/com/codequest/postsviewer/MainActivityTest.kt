package com.codequest.postsviewer

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.codequest.postsviewer.utils.atPositionOnView
import com.codequest.postsviewer.utils.wait
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun whenActivityIsStartedLoadingOverlayIsShown() {
        onView(withId(R.id.loadingView)).check(matches(isDisplayed()))
    }

    @Test
    fun whenPostsAreLoadedPostViewsAreShown() {
        onView(isRoot()).perform(wait(withId(R.id.recyclerView), 5000))

        onView(
            atPositionOnView(
                R.id.recyclerView,
                3,
                R.id.postTitle
            )
        ).check(matches(withText("eum et est occaecati")))
        onView(atPositionOnView(R.id.recyclerView, 3, R.id.postBody))
            .check(matches(withText("ullam et saepe reiciendis voluptatem adipisci\nsit amet autem assumenda provident rerum culpa\nquis hic commodi nesciunt rem tenetur doloremque ipsam iure\nquis sunt voluptatem rerum illo velit")))
    }
}
