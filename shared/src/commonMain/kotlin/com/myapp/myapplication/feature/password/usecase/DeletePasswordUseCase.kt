package com.myapp.myapplication.feature.password.usecase

import com.myapp.myapplication.db.LocalDatabaseRepository

interface IDeletePasswordUseCase{
    suspend fun execute(id: Long)
}
class DeletePasswordUseCaseImpl(private val dbRepository: LocalDatabaseRepository): IDeletePasswordUseCase {
    override suspend fun execute(id: Long) {
        dbRepository.deletePassword(id)
    }
}
