package com.myapp.pm.features.dialog

import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

interface FingerPrintAuthenticateListener{
    fun onAuthenticationSucceeded()
    fun onAuthenticationFailed(errorCode: Int = 0, errorString: String = "Wrong finger print")
}
class FingerPrintDialog(val activity: FragmentActivity) {

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    var listener: FingerPrintAuthenticateListener? = null

    fun show(){
        if(!this::biometricPrompt.isInitialized) {
            val executor = ContextCompat.getMainExecutor(activity)
            biometricPrompt = BiometricPrompt(activity, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(
                        errorCode: Int,
                        errString: CharSequence
                    ) {
                        super.onAuthenticationError(errorCode, errString)
                        listener?.onAuthenticationFailed(errorCode, errString.toString())
                    }

                    override fun onAuthenticationSucceeded(
                        result: BiometricPrompt.AuthenticationResult
                    ) {
                        super.onAuthenticationSucceeded(result)
                        listener?.onAuthenticationSucceeded()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        listener?.onAuthenticationFailed()
                    }
                })

            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Verify your identity")
                .setNegativeButtonText("Cancel")
                .build()
        }
        biometricPrompt.authenticate(promptInfo)
    }
}
