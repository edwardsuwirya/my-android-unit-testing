package com.enigmacamp.myunittesting.ui.main.userfind

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.enigmacamp.goldmarket.utils.ResourceState
import com.enigmacamp.myunittesting.data.repository.UserRepository

class UserFindViewModel constructor(val userRepository: UserRepository) : ViewModel() {
    var _findUserStatusLiveData = MutableLiveData<ResourceState>()
    val findUserStatueLiveData: LiveData<ResourceState>
        get() {
            return _findUserStatusLiveData
        }

    fun findUserInfo(userId: String) {
        _findUserStatusLiveData.value = ResourceState.loading()
        val selectedUser = userRepository.getUserInfo(userId)
        selectedUser?.let {
            _findUserStatusLiveData.value = ResourceState.success(it)
        } ?: run {
            _findUserStatusLiveData.value = ResourceState.error("No Data Found")
        }
    }
}