package com.myapp.myapplication.feature.password.usecase

import com.myapp.myapplication.db.LocalDatabaseRepository
import com.myapp.myapplication.db.entity.PasswordEntity

interface IAddPasswordUseCase {
    suspend fun execute(input: PasswordEntity)
}

class AddPasswordUseCaseImpl(private val repo: LocalDatabaseRepository): IAddPasswordUseCase{
    override suspend fun execute(input: PasswordEntity) {
        repo.insertPassword(input)
    }
}
