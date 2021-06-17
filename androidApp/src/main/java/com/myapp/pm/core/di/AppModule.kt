package com.myapp.pm.core.di

import com.myapp.myapplication.db.DatabaseDriverFactory
import com.myapp.myapplication.db.LocalDatabaseRepository
import com.myapp.myapplication.feature.password.usecase.GetPasswordsUseCaseImpl
import com.myapp.myapplication.feature.password.usecase.IGetPasswordsUseCase
import com.myapp.pm.features.pmlist.PasswordListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { DatabaseDriverFactory(get()) }
    single { LocalDatabaseRepository(get()) }
    single<IGetPasswordsUseCase> { GetPasswordsUseCaseImpl(get()) }
    viewModel { PasswordListViewModel(get()) }
}
