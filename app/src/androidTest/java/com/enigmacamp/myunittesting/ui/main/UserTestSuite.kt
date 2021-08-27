package com.enigmacamp.myunittesting.ui.main

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(SignUpFragmentTest::class, UserFindFragmentTest::class)
class UserTestSuite