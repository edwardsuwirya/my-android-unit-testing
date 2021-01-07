package com.enigmacamp.myunittesting.ui.main.userfind

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enigmacamp.goldmarket.utils.ResourceState
import com.enigmacamp.myunittesting.data.repository.UserRepository
import com.enigmacamp.myunittesting.utils.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserFindViewModel constructor(
    val userRepository: UserRepository,
    val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    var _findUserStatusLiveData = MutableLiveData<ResourceState>()
    val findUserStatueLiveData: LiveData<ResourceState>
        get() {
            return _findUserStatusLiveData
        }

    fun findUserInfo(userId: String) {
        viewModelScope.launch(dispatcherProvider.io()) {
            _findUserStatusLiveData.postValue(ResourceState.loading())
            val selectedUser = userRepository.getUserInfo(userId)
            selectedUser?.let {
                _findUserStatusLiveData.postValue(ResourceState.success(it))
            }
        }
    }
}