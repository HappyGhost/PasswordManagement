package com.myapp.pm.core

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T: ViewBinding>: Fragment() {

    private var _binding: T? = null
    val binding get() = _binding!!
    val appCompatActivity get() = activity as AppCompatActivity
    var shareReference: SharedPreferences? = null

    abstract fun setupViewBinding(inflater: LayoutInflater, container: ViewGroup?): T

    open fun getTitle(): String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = setupViewBinding(inflater, container)
        shareReference = activity?.getPreferences(Context.MODE_PRIVATE)
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

    fun getPrimaryPassword(): String {
        return shareReference?.getString(Constant.KEY_PRIMARY_PASSWORD, "") ?: ""
    }

    fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm = appCompatActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    fun hideSoftKeyboard() {
        val view = appCompatActivity.currentFocus
        view?.run {
            val imm = appCompatActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
