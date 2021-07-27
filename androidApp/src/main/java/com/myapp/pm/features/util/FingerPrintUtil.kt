package com.myapp.pm.features.util

import android.app.Activity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG

enum class FingerPrint{
    AVAILABLE, NOT_AVAILABLE, NONE_ENROLLED
}
class FingerPrintUtil {
    companion object {
        fun checkFingerPrintAvaialble(activity: Activity): FingerPrint {
            val biometricManager = BiometricManager.from(activity)
            when (biometricManager.canAuthenticate(BIOMETRIC_STRONG)) {
                BiometricManager.BIOMETRIC_SUCCESS ->{
                    return FingerPrint.AVAILABLE
                }
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    return FingerPrint.NONE_ENROLLED
                }
            }
            return FingerPrint.NOT_AVAILABLE
        }
    }
}
