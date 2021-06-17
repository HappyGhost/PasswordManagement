package com.myapp.pm.features.pmlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.myapplication.db.entity.PasswordEntity
import com.myapp.myapplication.feature.password.usecase.IGetPasswordsUseCase
import kotlinx.coroutines.launch


class PasswordListViewModel(val getPasswordUC: IGetPasswordsUseCase) : ViewModel() {

    val liveData = MutableLiveData<List<PasswordEntity>>()

    fun getPasswordList(): MutableLiveData<List<PasswordEntity>> {
        viewModelScope.launch {
            liveData.postValue(getPasswordUC.execute())
        }
        return liveData
    }
}
