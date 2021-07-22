package com.myapp.pm.features.dialog

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.myapp.pm.R
import com.myapp.pm.core.Constant
import com.myapp.pm.databinding.DialogConfirmPasswordBinding

fun interface ConfirmDialogListener {
    fun onSuccess()
}

class ConfirmPasswordDialog(private val activity: Activity) {
    var dialogListener: ConfirmDialogListener? = null
    private var shareReference = activity.getPreferences(Context.MODE_PRIVATE)
    private val binding: DialogConfirmPasswordBinding by lazy {
        val inflater = activity.layoutInflater
        DialogConfirmPasswordBinding.inflate(inflater, null, false)
    }
    lateinit var dialog: AlertDialog

    private fun clearInput() {
        binding.edtPrimaryPassword.setText("")
        binding.inputPrimaryPassword.error = null
    }

    private fun initView(dialog: AlertDialog) {
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val inputPassword = binding.edtPrimaryPassword.text.toString()
            val password = shareReference?.getString(Constant.KEY_PRIMARY_PASSWORD, "") ?: ""
            if (inputPassword == password) {
                dialog.dismiss()
                dialogListener?.onSuccess()
            } else {
                binding.inputPrimaryPassword.error =
                    activity.getString(R.string.dialog_wrong_password_message)
            }
        }
    }

    fun show(listener: ConfirmDialogListener) {
        dialogListener = listener
        show()
    }

    fun show() {
        if (!this::dialog.isInitialized) {
            val builder = AlertDialog.Builder(activity)
            builder.setView(binding.root)
                .setPositiveButton(R.string.btn_confirm, null)
                .setNegativeButton(R.string.btn_cancel, null)
            dialog = builder.create()
            dialog.setCanceledOnTouchOutside(false)
            dialog.setOnShowListener {
                initView(dialog)
            }
        }
        clearInput()
        dialog.show()
    }


}
