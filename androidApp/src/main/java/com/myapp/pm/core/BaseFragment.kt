package com.myapp.pm.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T: ViewBinding>: Fragment() {

    private var _binding: T? = null
    val binding get() = _binding!!
    val appCompatActivity get() = activity as AppCompatActivity

    abstract fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?): T

    open fun getTitle(): String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = setupViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = getTitle()
        if(title.isNotEmpty()){
            appCompatActivity.supportActionBar?.title = title
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
