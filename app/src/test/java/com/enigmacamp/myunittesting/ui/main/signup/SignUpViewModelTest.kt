package com.enigmacamp.myunittesting.ui.main.signup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.enigmacamp.myunittesting.data.model.UserRegistration
import com.enigmacamp.myunittesting.data.repository.UserRepository
import com.enigmacamp.myunittesting.utils.ResourceState
import com.enigmacamp.myunittesting.utils.ResourceStatus
import com.enigmacamp.myunittesting.utils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class SignUpViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    var userRepositoryMock: UserRepository? = null

    lateinit var signUpViewModel: SignUpViewModel

    @Before
    fun registerMock() {
        MockitoAnnotations.openMocks(this)
        signUpViewModel = SignUpViewModel(userRepositoryMock!!)
    }

    @Test
    fun userRegistration_onSuccessRegister_returnSuccessState() {
        val userDummy = UserRegistration(
            userId = "123",
            userName = "Dummy",
            password = "1", confirmedPassword = "1", email = "dummy@example.com"
        )

        runBlocking {
            `when`(userRepositoryMock?.registerUser(userDummy)).thenReturn(userDummy)
            signUpViewModel.userRegistration(userDummy)
            val actualLoadingResult = signUpViewModel.registrationStatusLiveData.getOrAwaitValue()
            assertThat(actualLoadingResult.status).isEqualTo(ResourceStatus.LOADING)

            val actualSuccessResult = signUpViewModel.registrationStatusLiveData.getOrAwaitValue()
            assertThat(actualSuccessResult.status).isEqualTo(ResourceStatus.SUCCESS)
            assertThat(actualSuccessResult.data).isEqualTo(true)
        }

    }
}