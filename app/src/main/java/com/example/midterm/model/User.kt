package com.example.midterm.model


data class QueryResult(
    val results: List<User>
)

data class User(
    val name: Name,
    val email: String,
    val nat: String
)

data class Name(
    val title: String,
    val first: String,
    val last: String
)
