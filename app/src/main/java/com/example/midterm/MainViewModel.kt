package com.example.midterm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.midterm.model.User
import com.example.midterm.rest.DataSource
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _users = MutableLiveData<Result<List<User>>>()
    val users: LiveData<Result<List<User>>> = _users

    fun getUsers() {
        viewModelScope.launch {
            try {
                val response = DataSource().loadUsers()
                _users.postValue(Result.success(response))
            } catch (e: Exception) {
                _users.postValue(Result.error(e))
            }
        }
    }
}

sealed class Result<out T : Any> {
    data class Loading(val message: String = "") : Result<Nothing>()
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    companion object {
        fun <T : Any> loading(message: String = ""): Result<T> = Loading(message)
        fun <T : Any> success(data: T): Result<T> = Success(data)
        fun error(exception: Exception): Result<Nothing> = Error(exception)
    }
}