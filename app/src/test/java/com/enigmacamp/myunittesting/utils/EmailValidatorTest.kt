package com.enigmacamp.myunittesting.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class EmailValidatorTest {

    @Test
    fun emailValidator_onValidPattern_returnTrue() {
        //Given
        val dummyEmail = "edo@example.com"

        //When
        val actualResult = EmailValidator.isValidEmail(dummyEmail)

        //Expectation
        assertThat(actualResult).isTrue()
    }

    @Test
    fun emailValidator_onInvalidPattern_returnFalse() {
        //Given
        val dummyEmail = "edo@"

        //When
        val actualResult = EmailValidator.isValidEmail(dummyEmail)

        //Expectation
        assertThat(actualResult).isFalse()
    }

}