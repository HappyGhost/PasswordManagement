package com.myapp.pm.core.di

import com.myapp.myapplication.db.DatabaseDriverFactory
import com.myapp.myapplication.db.LocalDatabaseRepository
import com.myapp.myapplication.feature.password.usecase.*
import com.myapp.pm.features.add.AddPasswordViewModel
import com.myapp.pm.features.detail.PasswordDetailViewModel
import com.myapp.pm.features.pmlist.PasswordListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { DatabaseDriverFactory(get()) }
    single { LocalDatabaseRepository(get()) }
    single<IGetPasswordsUseCase> { GetPasswordsUseCaseImpl(get()) }
    single<IAddPasswordUseCase> { AddPasswordUseCaseImpl(get()) }
    single<IUpdatePasswordUseCase> { UpdatePasswordUseCaseImpl(get()) }
    viewModel { PasswordListViewModel(get()) }
    viewModel { AddPasswordViewModel(get()) }
    viewModel { PasswordDetailViewModel(get()) }
}
