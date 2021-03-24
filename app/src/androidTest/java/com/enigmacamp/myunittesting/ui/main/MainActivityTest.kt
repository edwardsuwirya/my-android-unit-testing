package com.enigmacamp.myunittesting.ui.main

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.enigmacamp.myunittesting.R
import com.google.android.material.tabs.TabLayout
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Test
    fun mainActivity_onLaunch_success() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.tab_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun mainActivity_onLaunch_showSignupTab() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.tab_layout)).perform(selectTabAtPosition(0))

        onView(withId(R.id.signup_fragment)).check(matches(isDisplayed()))
    }

    @Test
    fun mainActivity_onTabFindClick_showFindTab() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.tab_layout)).perform(selectTabAtPosition(1))

        onView(withId(R.id.find_fragment)).check(matches(isDisplayed()))
    }

    fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "with tab at index $tabIndex"

            override fun getConstraints() =
                allOf(isDisplayed(), isAssignableFrom(TabLayout::class.java))

            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index $tabIndex"))
                        .build()

                tabAtIndex.select()
            }
        }
    }
}