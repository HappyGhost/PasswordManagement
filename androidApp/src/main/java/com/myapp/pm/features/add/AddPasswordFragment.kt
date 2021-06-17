package com.myapp.pm.features.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.myapp.pm.databinding.FragmentAddPasswordBinding
import org.koin.android.ext.android.bind

/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class AddPasswordFragment : Fragment() {

    private var _binding: FragmentAddPasswordBinding? = null
    private val _viewModel: AddPasswordViewModel by viewModel()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        _viewModel.addPasswordLiveData.observe(viewLifecycleOwner, { result ->
            if (result) {
                Toast.makeText(context, "add password successfully", Toast.LENGTH_LONG).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(context, "fail to add password", Toast.LENGTH_LONG).show()
            }
        })

        binding.btnAdd.setOnClickListener {
            if (checkTextFieldsValid(
                    binding.inputAccount,
                    binding.inputUsername,
                    binding.inputPassword,
                    binding.inputHint
                )
            ) {
                _viewModel.addPassword(
                    binding.tvAccountName.text.toString(),
                    binding.tvUsername.text.toString(),
                    binding.tvPassword.text.toString(),
                    binding.tvHint.text.toString()
                )
            }
        }
    }

    private fun checkTextFieldsValid(vararg inputs: TextInputLayout): Boolean {
        var result = true
        for (input in inputs) {
            result = !checkTextFieldEmpty(input) && result
        }
        return result
    }

    private fun checkTextFieldEmpty(input: TextInputLayout): Boolean {
        return if (input.editText?.text.toString().isEmpty()) {
            input.error = "this field shouldn't be empty"
            true
        } else {
            input.error = null
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
