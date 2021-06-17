package com.myapp.myapplication.db

import com.myapp.myapplication.db.entity.PasswordEntity


class LocalDatabaseRepository(driverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(driverFactory.createDiver())
    private val dbQuery = database.appDatabaseQueries

    internal fun getAllPassword(): List<PasswordEntity> {
        return dbQuery.selectAllPassword { id, accountName, username, password, hint ->
            PasswordEntity(
                id,
                accountName,
                username,
                password,
                hint?: ""
            )
        }.executeAsList()
    }

    internal fun insertPassword(entity: PasswordEntity) {
        dbQuery.insertPassword(entity.accountName, entity.username, entity.password, entity.hint)
    }

    internal fun deletePassword(rowId: Long) {
        dbQuery.deletePassword(rowId)
    }
}
