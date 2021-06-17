package com.myapp.myapplication.feature.password.usecase

import com.myapp.myapplication.db.LocalDatabaseRepository
import com.myapp.myapplication.db.entity.PasswordEntity

interface IGetPasswordsUseCase {

    suspend fun execute() : List<PasswordEntity>
}

class GetPasswordsUseCaseImpl(val repository: LocalDatabaseRepository) : IGetPasswordsUseCase {

    @Throws(Exception::class)
    override suspend fun execute() : List<PasswordEntity> {
        return repository.getAllPassword()
    }
}
