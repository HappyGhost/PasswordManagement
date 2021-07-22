package com.myapp.pm.features.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myapp.pm.R
import com.myapp.pm.core.BaseFragment
import com.myapp.pm.core.Constant
import com.myapp.pm.databinding.FragmentSettingBinding
import com.myapp.pm.features.dialog.ConfirmPasswordDialog


class SettingFragment : BaseFragment<FragmentSettingBinding>() {

    private val confirmDialog: ConfirmPasswordDialog by lazy {
        ConfirmPasswordDialog(appCompatActivity)
    }

    private var forceChange: Boolean = false

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
        }

        binding.switchSecurityCheck.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!forceChange && getPrimaryPassword().isNotEmpty() && !isChecked) {
                buttonView.isChecked = true
                confirmDialog.show {
                    forceChange = true
                    buttonView.isChecked = false
                    saveSecurityCheck(false)
                }
            } else {
                forceChange = false
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

    private fun saveSecurityCheck(isCheck: Boolean){
        shareReference?.edit()?.putBoolean(Constant.KEY_SECURITY_CHECK, isCheck)?.apply()
    }
}
