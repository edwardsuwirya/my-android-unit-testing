package com.enigmacamp.myunittesting.ui.main

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.enigmacamp.myunittesting.R
import com.enigmacamp.myunittesting.ui.main.signup.SignupFragment
import org.junit.Test

class SignUpFragmentTest {
    @Test
    fun signUpFragment_onLaunch_success() {
        launchFragmentInContainer<SignupFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.userName_textView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}