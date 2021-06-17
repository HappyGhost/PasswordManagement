package com.myapp.myapplication.db

import com.squareup.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory {
    fun createDiver(): SqlDriver
}
