package com.enigmacamp.myunittesting.ui.main

import android.view.View
import androidx.test.espresso.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.enigmacamp.myunittesting.R
import com.google.android.material.tabs.TabLayout
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivity_onLaunch_success() {
        Espresso.onView(ViewMatchers.withId(R.id.tab_layout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    private fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getConstraints() =
                CoreMatchers.allOf(
                    ViewMatchers.isDisplayed(),
                    ViewMatchers.isAssignableFrom(TabLayout::class.java)
                )


            override fun getDescription() = "Tab at index $tabIndex"

            override fun perform(uiController: UiController?, view: View?) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab =
                    tabLayout.getTabAt(tabIndex) ?: throw  PerformException.Builder().withCause(
                        Throwable("No tab at index $tabIndex")
                    ).build()
                tabAtIndex.select()
            }

        }
    }

    @Test
    fun mainActivity_onTabFindClick_showFindTab() {
        Espresso.onView(ViewMatchers.withId(R.id.tab_layout)).perform(selectTabAtPosition(1))
        Espresso.onView(ViewMatchers.withId(R.id.find_fragment))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}