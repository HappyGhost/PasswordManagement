package com.myapp.pm.features.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.myapp.pm.databinding.FragmentDetailBinding
import com.myapp.pm.features.SharedViewModel


class PasswordDetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        sharedViewModel.passwordUiModel?.let {
            binding.tvAccountName.setText(it.accountName)
            binding.tvUsername.setText(it.username)
            binding.tvPassword.setText(it.password)
            binding.tvHint.setText(it.passHint)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
