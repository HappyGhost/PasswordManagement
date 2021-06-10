package com.myapp.myapplication.db.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PasswordEntity(
    @SerialName("id")
    val id: Long = 0,
    @SerialName("accountName")
    val accountName: String,
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
    @SerialName("pass_hint")
    val hint: String
)
