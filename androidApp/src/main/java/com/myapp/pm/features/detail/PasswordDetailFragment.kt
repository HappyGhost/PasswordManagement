package com.myapp.pm.features.detail

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.myapp.pm.R
import com.myapp.pm.core.BaseFragment
import com.myapp.pm.databinding.FragmentDetailBinding
import com.myapp.pm.features.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class PasswordDetailFragment : BaseFragment<FragmentDetailBinding>() {

    enum class DisplayMode {
        DETAIL_MODE,
        EDIT_MODE
    }

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val detailViewModel: PasswordDetailViewModel by viewModel()
    private var displayMode = DisplayMode.DETAIL_MODE

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBinding {
        return FragmentDetailBinding.inflate(inflater, container, false)
    }

    override fun getTitle(): String = getString(R.string.fragment_detail_action_bar_title)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initView()
        initViewModel()
    }

    private fun initViewModel() {
        detailViewModel.updatePasswordLiveData.observe(viewLifecycleOwner, { result ->
            if (result.isSuccess) {
                Toast.makeText(activity, "Update successfully", Toast.LENGTH_LONG).show()
                displayMode = DisplayMode.DETAIL_MODE
                sharedViewModel.passwordUiModel = result.getOrNull()
                updateUiBehavior()
            } else {
                Toast.makeText(activity, "Update fail", Toast.LENGTH_LONG).show()
            }
        })

        detailViewModel.deletePasswordLiveData.observe(viewLifecycleOwner, { result ->
            if (result.isSuccess) {
                Toast.makeText(activity, "Delete successfully", Toast.LENGTH_LONG).show()
                findNavController().navigateUp()
            } else {
                Toast.makeText(activity, "Delete fail", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initView() {
        updateUiBehavior()
        binding.btnUpdate.setOnClickListener {
            detailViewModel.updatePassword(
                sharedViewModel.passwordUiModel!!.id,
                binding.tvAccountName.text.toString(),
                binding.tvUsername.text.toString(),
                binding.tvPassword.text.toString(),
                binding.tvHint.text.toString()
            )
        }
        binding.imgShowPassword.setOnClickListener {
            it.isSelected = !it.isSelected
            if (it.isSelected) {
                binding.tvPassword.transformationMethod = null
            } else {
                binding.tvPassword.transformationMethod = PasswordTransformationMethod()
            }
        }
    }

    private fun showPasswordInfoFromShareModel() {
        sharedViewModel.passwordUiModel?.let {
            binding.tvAccountName.setText(it.accountName)
            binding.tvUsername.setText(it.username)
            binding.tvPassword.setText(it.password)
            binding.tvHint.setText(it.passHint)
        }
    }

    private fun updateUiBehavior() {
        if (displayMode == DisplayMode.EDIT_MODE) {
            binding.btnUpdate.visibility = View.VISIBLE
            binding.inputAccount.isEnabled = true
            binding.inputUsername.isEnabled = true
            binding.inputPassword.isEnabled = true
            binding.inputHint.isEnabled = true
            appCompatActivity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        } else {
            binding.btnUpdate.visibility = View.INVISIBLE
            binding.inputAccount.isEnabled = false
            binding.inputUsername.isEnabled = false
            binding.inputPassword.isEnabled = false
            binding.inputHint.isEnabled = false
            appCompatActivity.supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
            showPasswordInfoFromShareModel()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_password_detail, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (displayMode == DisplayMode.EDIT_MODE) {
                    displayMode = DisplayMode.DETAIL_MODE
                    updateUiBehavior()
                    return true
                }
            }
            R.id.menu_edit -> {
                displayMode = DisplayMode.EDIT_MODE
                updateUiBehavior()
            }
            R.id.menu_delete -> detailViewModel.deletePassword(sharedViewModel.passwordUiModel!!.id)
        }
        return super.onOptionsItemSelected(item)
    }
}
