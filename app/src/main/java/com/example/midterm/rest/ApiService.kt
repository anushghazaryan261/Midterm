package com.example.midterm.rest

import com.example.midterm.model.QueryResult
import retrofit2.http.GET

interface ApiService {

    @GET("/api/?inc=nat,name,email&results=100")
    suspend fun getUsers(): QueryResult
}