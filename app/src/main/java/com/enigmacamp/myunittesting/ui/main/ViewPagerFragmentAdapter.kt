package com.enigmacamp.myunittesting.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.enigmacamp.myunittesting.ui.main.signup.SignupFragment
import com.enigmacamp.myunittesting.ui.main.userfind.UserFindFragment


class ViewPagerFragmentAdapter(val titles: List<String>, fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {


    override fun getItemCount(): Int {
        return titles.size
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return SignupFragment()
            1 -> return UserFindFragment()
            else -> return SignupFragment()
        }
    }
}