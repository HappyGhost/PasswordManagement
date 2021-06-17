package com.myapp.pm.features.add

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.myapplication.db.entity.PasswordEntity
import com.myapp.myapplication.feature.password.usecase.IAddPasswordUseCase
import kotlinx.coroutines.launch
import java.lang.Exception


class AddPasswordViewModel(val addPasswordUC: IAddPasswordUseCase) : ViewModel() {
    val addPasswordLiveData = MutableLiveData<Boolean>()

    fun addPassword(
        account: String,
        username: String,
        password: String,
        hint: String
    ): MutableLiveData<Boolean> {
        viewModelScope.launch {
            try {
                addPasswordUC.execute(
                    PasswordEntity(
                        accountName = account,
                        username = username,
                        password = password,
                        hint = hint
                    )
                )
                addPasswordLiveData.postValue(true)
            } catch (ex: Exception) {
                addPasswordLiveData.postValue(false)
            }
        }
        return addPasswordLiveData
    }
}
