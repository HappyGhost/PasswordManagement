package com.myapp.pm.features

import androidx.lifecycle.ViewModel
import com.myapp.pm.features.uimodel.PasswordUiModel


class SharedViewModel: ViewModel() {
    var passwordUiModel: PasswordUiModel? = null
}
