package com.enigmacamp.myunittesting.ui.main

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.enigmacamp.myunittesting.R
import com.enigmacamp.myunittesting.ui.main.userfind.UserFindFragment
import org.junit.Test

class UserFindFragmentTest {

    @Test
    fun userFindFragment_onSuccessFind_showUserInfo() {
        launchFragmentInContainer<UserFindFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.findUser_textView))
            .perform(ViewActions.typeText("dummy"))
        Espresso.onView(ViewMatchers.withId(R.id.find_button)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.result_textView))
            .check(ViewAssertions.matches(ViewMatchers.withText("dummy")))
    }
}