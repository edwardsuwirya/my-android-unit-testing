package com.enigmacamp.myunittesting.ui.main.userfind

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.enigmacamp.goldmarket.utils.ResourceStatus
import com.enigmacamp.myunittesting.R
import com.enigmacamp.myunittesting.data.api.RetrofitInstance
import com.enigmacamp.myunittesting.data.dao.MyDatabase
import com.enigmacamp.myunittesting.data.model.UserRegistration
import com.enigmacamp.myunittesting.data.repository.UserRepository
import com.enigmacamp.myunittesting.databinding.FragmentUserFindBinding
import com.enigmacamp.myunittesting.utils.DefaultDispatcherProvider


/**
 * A simple [Fragment] subclass.
 * Use the [UserFindFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFindFragment : Fragment() {
    lateinit var viewModel: UserFindViewModel
    lateinit var findBinding: FragmentUserFindBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_find, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findBinding = FragmentUserFindBinding.bind(view)
        findBinding.apply {
            findButton.setOnClickListener {
                val userIdText = findUserTextView.text
                viewModel.findUserInfo(userIdText.toString())
            }
        }
        initViewModel()
        subscribe()
    }

    fun initViewModel() {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val db = MyDatabase.getDatabase(requireContext())
                val repository = UserRepository(db.userDao(),RetrofitInstance.userRegistrationApi)
                return UserFindViewModel(repository, DefaultDispatcherProvider()) as T
            }

        }).get(UserFindViewModel::class.java)
    }

    fun subscribe() {
        viewModel.findUserStatueLiveData.observe(viewLifecycleOwner, {
            when (it.status) {
                ResourceStatus.LOADING -> Log.d("UserFind", "Loading")
                ResourceStatus.SUCCESS -> findBinding.resultTextView.text =
                    (it.data as UserRegistration).toString()
                ResourceStatus.ERROR -> findBinding.resultTextView.text = it.message
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserFindFragment()
    }
}