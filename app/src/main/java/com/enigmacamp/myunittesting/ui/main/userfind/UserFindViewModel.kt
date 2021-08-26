package com.enigmacamp.myunittesting.ui.main.userfind

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enigmacamp.myunittesting.utils.ResourceState
import com.enigmacamp.myunittesting.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserFindViewModel constructor(
    val userRepository: UserRepository
) : ViewModel() {
    var _findUserStatusLiveData = MutableLiveData<ResourceState>()
    val findUserStatueLiveData: LiveData<ResourceState>
        get() {
            return _findUserStatusLiveData
        }

    fun findUserInfo(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _findUserStatusLiveData.postValue(ResourceState.loading())
            val selectedUser = userRepository.getUserInfo(userId)
            _findUserStatusLiveData.postValue(ResourceState.success(selectedUser))
        }
    }
}