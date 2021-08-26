package com.enigmacamp.myunittesting.utils

class ResourceState(
    val status: ResourceStatus,
    val data: Any?,
    val message: String?
) {
    companion object {
        fun success(data: Any?): ResourceState =
            ResourceState(status = ResourceStatus.SUCCESS, data = data, message = null)

        fun error(message: String?): ResourceState =
            ResourceState(status = ResourceStatus.ERROR, data = null, message = message)

        fun loading(): ResourceState =
            ResourceState(status = ResourceStatus.LOADING, data = null, message = null)
    }
}