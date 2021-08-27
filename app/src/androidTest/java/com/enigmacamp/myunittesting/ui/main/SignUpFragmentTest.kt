package com.enigmacamp.myunittesting.ui.main

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.enigmacamp.myunittesting.R
import com.enigmacamp.myunittesting.ToastMatcher
import com.enigmacamp.myunittesting.ui.main.signup.SignupFragment
import org.junit.Test

class SignUpFragmentTest {
    @Test
    fun signUpFragment_onLaunch_success() {
        launchFragmentInContainer<SignupFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.userName_textView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.email_textView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.password_textView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.confirmedPassword_textView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.signUp_button))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun signUpFragment_onSignupClick_registerSuccess() {
        launchFragmentInContainer<SignupFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.userName_textView))
            .perform(ViewActions.typeText("dummy"))
        Espresso.onView(ViewMatchers.withId(R.id.email_textView))
            .perform(ViewActions.typeText("dummy@example.com"))
        Espresso.onView(ViewMatchers.withId(R.id.password_textView))
            .perform(ViewActions.typeText("dummy123"))
        Espresso.onView(ViewMatchers.withId(R.id.confirmedPassword_textView))
            .perform(ViewActions.typeText("dummy123"))
        Espresso.onView(ViewMatchers.withId(R.id.signUp_button))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Success")).inRoot(ToastMatcher())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
}