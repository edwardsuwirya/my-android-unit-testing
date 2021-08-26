package com.enigmacamp.myunittesting.ui.main.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enigmacamp.goldmarket.utils.ResourceState
import com.enigmacamp.myunittesting.data.model.UserRegistration
import com.enigmacamp.myunittesting.data.repository.UserRepository
import com.enigmacamp.myunittesting.utils.RegistrationUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel constructor(
    val userRepository: UserRepository
) : ViewModel() {

    var _registrationStatusLiveData = MutableLiveData<ResourceState>()
    val registrationStatusLiveData: LiveData<ResourceState>
        get() {
            return _registrationStatusLiveData
        }


    fun userRegistration(userRegistration: UserRegistration) {
        viewModelScope.launch(Dispatchers.IO) {
            _registrationStatusLiveData.postValue(ResourceState.loading())
            val result = RegistrationUtil.validateRegistrationInput(
                userRegistration.userName,
                userRegistration.password,
                userRegistration.confirmedPassword,
                userRegistration.email
            )
            Log.d("Registration", "userRegistration: $result")
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