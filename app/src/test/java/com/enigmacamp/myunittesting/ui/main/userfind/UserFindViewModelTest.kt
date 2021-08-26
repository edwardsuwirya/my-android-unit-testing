package com.enigmacamp.myunittesting.ui.main.userfind

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.enigmacamp.myunittesting.utils.ResourceStatus
import com.enigmacamp.myunittesting.data.model.UserRegistration
import com.enigmacamp.myunittesting.data.repository.UserRepository
import com.enigmacamp.myunittesting.utils.MainCoroutineRule
import com.enigmacamp.myunittesting.utils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class UserFindViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    var userRepositoryMock: UserRepository? = null

    lateinit var userFindViewModel: UserFindViewModel

    @Before
    fun registerMock() {
        MockitoAnnotations.openMocks(this)
        userFindViewModel = UserFindViewModel(userRepositoryMock!!)
    }

    @Test
    fun userFind_onSuccessFindUser_returnResourceStateSuccess() {
        val userDummy = UserRegistration(
            userId = "123",
            userName = "Dummy",
            password = "", confirmedPassword = "", email = ""
        )
        `when`(userRepositoryMock?.getUserInfo("dummy")).thenReturn(userDummy)
        userFindViewModel.findUserInfo("dummy")
        val actualResult = userFindViewModel.findUserStatueLiveData.getOrAwaitValue()
        assertThat(actualResult.status).isEqualTo(ResourceStatus.LOADING)

        val successResult = userFindViewModel.findUserStatueLiveData.getOrAwaitValue()
        assertThat(successResult.status).isEqualTo(ResourceStatus.SUCCESS)
        assertThat((successResult.data as UserRegistration).userId).isEqualTo("123")

    }
}