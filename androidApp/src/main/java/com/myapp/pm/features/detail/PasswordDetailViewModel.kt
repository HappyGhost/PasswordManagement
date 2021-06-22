package com.myapp.pm.features.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.myapplication.db.entity.PasswordEntity
import com.myapp.myapplication.feature.password.usecase.IUpdatePasswordUseCase
import com.myapp.pm.features.uimodel.PasswordUiModel
import com.myapp.pm.features.uimodel.toPasswordUiModel
import kotlinx.coroutines.launch
import java.lang.Exception


class PasswordDetailViewModel(private val updatePasswordUC: IUpdatePasswordUseCase) : ViewModel() {
    val updatePasswordLiveData = MutableLiveData<Result<PasswordUiModel>>()

    fun updatePassword(id: Long, accountName: String, username: String, password: String, hint: String) {
        viewModelScope.launch {
            val entity = PasswordEntity(id,accountName, username, password, hint)
            try {
                updatePasswordUC.execute(entity)
                updatePasswordLiveData.postValue(Result.success(entity.toPasswordUiModel()))
            } catch (exception: Exception) {
                updatePasswordLiveData.postValue(Result.failure(exception))
            }
        }
    }
}
