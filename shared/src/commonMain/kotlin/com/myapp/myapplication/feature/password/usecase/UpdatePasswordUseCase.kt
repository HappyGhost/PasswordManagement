package com.myapp.myapplication.feature.password.usecase

import com.myapp.myapplication.db.LocalDatabaseRepository
import com.myapp.myapplication.db.entity.PasswordEntity

interface IUpdatePasswordUseCase{
    suspend fun execute(entity: PasswordEntity)
}

class UpdatePasswordUseCaseImpl(val dbRepository: LocalDatabaseRepository): IUpdatePasswordUseCase {
    override suspend fun execute(entity: PasswordEntity) {
        dbRepository.updatePassword(entity)
    }
}
