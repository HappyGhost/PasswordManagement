package com.myapp.pm.features.uimodel

import com.myapp.myapplication.db.entity.PasswordEntity


data class PasswordUiModel(
    val id: Long,
    val accountName: String,
    val username: String,
    val password: String,
    val passHint: String
)

fun PasswordEntity.toPasswordUiModel(): PasswordUiModel {
    return PasswordUiModel(id, accountName, username, password, hint)
}
