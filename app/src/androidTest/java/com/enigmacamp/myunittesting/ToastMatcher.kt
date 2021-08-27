package com.enigmacamp.myunittesting

import android.os.IBinder
import android.view.WindowManager
import androidx.test.espresso.Root
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher


class ToastMatcher : TypeSafeMatcher<Root>() {
    override fun describeTo(description: Description?) {
        description?.appendText("Is A Toast")
    }

    override fun matchesSafely(root: Root): Boolean {
        val type: Int = root.getWindowLayoutParams()?.get()?.type ?: 0
        if (type == WindowManager.LayoutParams.TYPE_TOAST) {
            val windowToken: IBinder = root.decorView.windowToken
            val appToken: IBinder = root.decorView.applicationWindowToken
            if (windowToken == appToken) {
                return true
            }
        }
        return false
    }
}