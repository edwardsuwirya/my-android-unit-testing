package com.enigmacamp.myunittesting.ui.main.signup

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.enigmacamp.goldmarket.utils.ResourceState
import com.enigmacamp.myunittesting.R
import com.enigmacamp.myunittesting.data.model.UserRegistration
import com.enigmacamp.myunittesting.data.repository.UserRepository
import com.enigmacamp.myunittesting.utils.DefaultDispatcherProvider
import com.nhaarman.mockitokotlin2.doNothing
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@MediumTest
@RunWith(AndroidJUnit4::class)
class SignupFragmentTest {
    @Test
    fun signUpFragment_onLaunch_success() {
        launchFragmentInContainer<SignupFragment>()
        onView(withId(R.id.userName_textView)).check(matches(isDisplayed()))
        onView(withId(R.id.email_textView)).check(matches(isDisplayed()))
        onView(withId(R.id.password_textView)).check(matches(isDisplayed()))
        onView(withId(R.id.confirmedPassword_textView)).check(matches(isDisplayed()))
    }

    @Test
    fun signupFragment_onSignupClick_registerSuccess() {
        launchFragmentInContainer { ->
            SignupFragment().apply {
//                doNothing().`when`(viewModel.userRegistration(any()))
//                viewModel._registrationStatusLiveData.value = ResourceState.success(true)
            }
        }
        val dummyUser = UserRegistration(
            userName = "dummy",
            password = "1",
            confirmedPassword = "1",
            email = "test@example.com"
        )
        onView(withId(R.id.userName_textView)).perform(typeText("dummy"))
        onView(withId(R.id.email_textView)).perform(typeText("dummy@example.com"))
        onView(withId(R.id.password_textView)).perform(typeText("123456"))
        onView(withId(R.id.confirmedPassword_textView)).perform(typeText("123456"))
        onView(withId(R.id.signUp_button)).perform(click())
    }
}