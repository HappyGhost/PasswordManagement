package com.myapp.pm.features.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.myapp.pm.R
import com.myapp.pm.core.BaseFragment
import com.myapp.pm.core.Constant
import com.myapp.pm.databinding.FragmentSettingBinding
import com.myapp.pm.features.dialog.ConfirmPasswordDialog
import com.myapp.pm.features.dialog.FingerPrintAuthenticateListener
import com.myapp.pm.features.dialog.FingerPrintDialog
import com.myapp.pm.features.util.FingerPrint.NOT_AVAILABLE
import com.myapp.pm.features.util.FingerPrintUtil


class SettingFragment : BaseFragment<FragmentSettingBinding>() {

    private val confirmDialog: ConfirmPasswordDialog by lazy {
        ConfirmPasswordDialog(appCompatActivity)
    }

    private val fingerPrintDialog: FingerPrintDialog by lazy {
        FingerPrintDialog(appCompatActivity)
    }

    private var forceSecurityChange: Boolean = false
    private var forceFingerPrintChange: Boolean = false

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(inflater, container, false)
    }

    override fun getTitle(): String = getString(R.string.setting_title)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        shareReference?.run {
            binding.switchSecurityCheck.isChecked = getBoolean(Constant.KEY_SECURITY_CHECK, false)
            binding.switchFingerPrintCheck.isChecked = getBoolean(Constant.KEY_FINGER_PRINT_CHECK, false)
        }

        initSecurityCheckBehavior()
        initFingerPrintSettingBehavior()
    }

    private fun initFingerPrintSettingBehavior() {
        if (FingerPrintUtil.checkFingerPrintAvaialble(appCompatActivity) == NOT_AVAILABLE) {
            binding.clFingerPrint.visibility = View.GONE
        } else {
            binding.clFingerPrint.visibility = View.VISIBLE
            binding.switchFingerPrintCheck.setOnCheckedChangeListener { buttonView, isChecked ->
                if (!forceFingerPrintChange && !isChecked) {
                    buttonView.isChecked = true
                    fingerPrintDialog.listener = object : FingerPrintAuthenticateListener {
                        override fun onAuthenticationSucceeded() {
                            forceFingerPrintChange = true
                            buttonView.isChecked = false
                            saveFingerSetting(false)
                        }

                        override fun onAuthenticationFailed(errorCode: Int, errorString: String) {
                            Toast.makeText(activity, errorString, Toast.LENGTH_LONG).show()
                        }

                    }
                    fingerPrintDialog.show()
                } else {
                    forceFingerPrintChange = false
                    saveFingerSetting(isChecked)
                }
            }
        }
    }

    private fun initSecurityCheckBehavior() {
        binding.switchSecurityCheck.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!forceSecurityChange && getPrimaryPassword().isNotEmpty() && !isChecked) {
                buttonView.isChecked = true
                confirmDialog.show {
                    forceSecurityChange = true
                    buttonView.isChecked = false
                    saveSecurityCheck(false)
                }
            } else {
                forceSecurityChange = false
                saveSecurityCheck(isChecked)
            }
        }

        binding.tvChangePassword.apply {
            setOnClickListener {
                val currentText = text.toString()
                val updateText = getString(R.string.setting_save_password_text)
                if (currentText != updateText) {
                    if (getPrimaryPassword().isEmpty()) {
                        enablePassword()
                        setText(R.string.setting_save_password_text)
                    } else {
                        confirmDialog.show { enablePassword() }
                    }
                } else {
                    savePassword(binding.edtPrimaryPassword.text.toString())
                    setText(R.string.setting_change_text)
                    showHiddenPrimaryPassword()
                }
            }
        }
        showHiddenPrimaryPassword()
    }

    private fun showHiddenPrimaryPassword() {
        val hiddenPassword = "*".repeat(getPrimaryPassword().length)
        binding.edtPrimaryPassword.setText(hiddenPassword)
    }

    private fun enablePassword() {
        binding.edtPrimaryPassword.isEnabled = true
        showSoftKeyboard(binding.edtPrimaryPassword)
        binding.edtPrimaryPassword.setText("")
        binding.tvChangePassword.setText(R.string.setting_save_password_text)
    }

    private fun savePassword(pass: String) {
        shareReference?.edit()?.putString(Constant.KEY_PRIMARY_PASSWORD, pass)?.apply()
        binding.edtPrimaryPassword.isEnabled = false
        binding.tvChangePassword.setText(R.string.setting_change_text)
        hideSoftKeyboard()
        showHiddenPrimaryPassword()
    }

    private fun saveSecurityCheck(isCheck: Boolean) {
        shareReference?.edit()?.putBoolean(Constant.KEY_SECURITY_CHECK, isCheck)?.apply()
    }

    private fun saveFingerSetting(isCheck: Boolean) {
        shareReference?.edit()?.putBoolean(Constant.KEY_FINGER_PRINT_CHECK, isCheck)?.apply()
    }
}
