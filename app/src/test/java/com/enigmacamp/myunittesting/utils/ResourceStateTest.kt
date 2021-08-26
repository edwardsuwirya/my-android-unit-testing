package com.enigmacamp.myunittesting.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ResourceStateTest {
    @Test
    fun resourceState_onSuccessEvent_returnResourceStateSuccess() {
        val dummyState = ResourceState.success("sukses")
        assertThat(dummyState.data).isEqualTo("sukses")
        assertThat(dummyState.message).isNull()
        assertThat(dummyState.status).isEqualTo(ResourceStatus.SUCCESS)
    }
}