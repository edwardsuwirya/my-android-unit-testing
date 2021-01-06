package com.enigmacamp.myunittesting.ui.main.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enigmacamp.goldmarket.utils.ResourceState
import com.enigmacamp.myunittesting.data.model.UserRegistration
import com.enigmacamp.myunittesting.data.repository.UserRepository
import com.enigmacamp.myunittesting.utils.RegistrationUtil

class SignUpViewModel constructor(val userRepository: UserRepository) : ViewModel() {

    var _registrationStatusLiveData = MutableLiveData<ResourceState>()
    val registrationStatusLiveData: LiveData<ResourceState>
        get() {
            return _registrationStatusLiveData
        }


    fun userRegistration(userRegistration: UserRegistration) {
        _registrationStatusLiveData.value = ResourceState.loading()
        val result = RegistrationUtil.validateRegistrationInput(
            userRegistration.userName,
            userRegistration.password,
            userRegistration.confirmedPassword,
            userRegistration.email
        )
        if (result) {
            val registeredUser = userRepository.registerUser(userRegistration)
            _registrationStatusLiveData.value = ResourceState.success(registeredUser)
        } else {
            _registrationStatusLiveData.value = ResourceState.error("Failed")
        }
    }

}