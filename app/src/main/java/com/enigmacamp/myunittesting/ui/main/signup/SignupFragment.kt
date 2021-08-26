package com.enigmacamp.myunittesting.ui.main.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.enigmacamp.myunittesting.utils.ResourceStatus
import com.enigmacamp.myunittesting.R
import com.enigmacamp.myunittesting.data.api.RetrofitInstance
import com.enigmacamp.myunittesting.data.dao.MyDatabase
import com.enigmacamp.myunittesting.data.model.UserRegistration
import com.enigmacamp.myunittesting.data.repository.UserRepository
import com.enigmacamp.myunittesting.databinding.FragmentSignupBinding

/**
 * A simple [Fragment] subclass.
 * Use the [SignupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignupFragment : Fragment() {
    lateinit var viewModel: SignUpViewModel
    lateinit var signUpBinding: FragmentSignupBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpBinding = FragmentSignupBinding.bind(view)
        signUpBinding.apply {
            signUpButton.setOnClickListener {
                val userName = userNameTextView.text
                val email = emailTextView.text
                val password = passwordTextView.text
                val confirmedPassword = confirmedPasswordTextView.text
                viewModel.userRegistration(
                    UserRegistration(
                        userName = userName.toString(),
                        password = password.toString(),
                        confirmedPassword = confirmedPassword.toString(),
                        email = email.toString()
                    )
                )
            }
        }
        initViewModel()
        subscribe()
    }

    fun initViewModel() {
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val db = MyDatabase.getDatabase(requireContext())
                val repository = UserRepository(db.userDao(), RetrofitInstance.userRegistrationApi)
                return SignUpViewModel(repository) as T
            }

        }).get(SignUpViewModel::class.java)
    }

    fun subscribe() {
        viewModel.registrationStatusLiveData.observe(viewLifecycleOwner, {
            when (it.status) {
                ResourceStatus.LOADING -> Log.d("Main", "Loading")
                ResourceStatus.SUCCESS -> {
                    val resultState = it.data as Boolean
                    if (resultState) {
                        Toast.makeText(
                            requireContext(),
                            "Success",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Error",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    clearForm()
                }
                ResourceStatus.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        it.message,
                        Toast.LENGTH_LONG
                    ).show()
                    clearForm()
                }
            }
        })
    }

    fun clearForm() {
        signUpBinding.userNameTextView.setText("")
        signUpBinding.emailTextView.setText("")
        signUpBinding.passwordTextView.setText("")
        signUpBinding.confirmedPasswordTextView.setText("")
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignupFragment()
    }
}