package com.bpplanner.bpp.model.base

sealed class ApiStatus<out T> {
    object Loading : ApiStatus<Nothing>()
    class Success<T>(val code: Int, val data: T) : ApiStatus<T>()
    class Error(val code: Int, val message: String) : ApiStatus<Nothing>()
    open class ValidateFail() : ApiStatus<Nothing>()
}


sealed class A : ApiStatus.ValidateFail() {

}