package com.enigmacamp.myunittesting.ui.main.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enigmacamp.goldmarket.utils.ResourceState
import com.enigmacamp.myunittesting.data.model.UserRegistration
import com.enigmacamp.myunittesting.data.repository.UserRepository
import com.enigmacamp.myunittesting.utils.DispatcherProvider
import com.enigmacamp.myunittesting.utils.RegistrationUtil
import kotlinx.coroutines.launch

class SignUpViewModel constructor(
    val userRepository: UserRepository,
    val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    var _registrationStatusLiveData = MutableLiveData<ResourceState>()
    val registrationStatusLiveData: LiveData<ResourceState>
        get() {
            return _registrationStatusLiveData
        }


    fun userRegistration(userRegistration: UserRegistration) {
        viewModelScope.launch(dispatcherProvider.io()) {
            _registrationStatusLiveData.postValue(ResourceState.loading())
            val result = RegistrationUtil.validateRegistrationInput(
                userRegistration.userName,
                userRegistration.password,
                userRegistration.confirmedPassword,
                userRegistration.email
            )
            if (result) {

                val registeredUser = userRepository.registerUser(userRegistration)
                registeredUser?.let {
                    _registrationStatusLiveData.postValue(ResourceState.success(true))
                } ?: run {
                    _registrationStatusLiveData.postValue(ResourceState.error("Failed"))

                }
            } else {
                _registrationStatusLiveData.postValue(ResourceState.error("Failed"))
            }
        }
    }

}