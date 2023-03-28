package com.example.midterm.rest

import com.example.midterm.model.User

class DataSource {

    suspend fun loadUsers(): List<User> {
        return RetrofitHelper.getInstance().create(ApiService::class.java).getUsers().results
    }
}