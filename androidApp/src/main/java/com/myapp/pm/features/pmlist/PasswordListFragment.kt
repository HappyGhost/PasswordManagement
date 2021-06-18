package com.myapp.pm.features.pmlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.myapp.pm.R
import com.myapp.pm.features.pmlist.adapter.PasswordAdapter
import com.myapp.pm.databinding.FragmentPasswordListBinding
import com.myapp.pm.features.SharedViewModel
import com.myapp.pm.features.pmlist.adapter.toPasswordUiModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A fragment representing a list of Items.
 */
class PasswordListFragment : Fragment() {

    private lateinit var binding: FragmentPasswordListBinding
    private val viewModel: PasswordListViewModel by viewModel()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val passwordAdapter = PasswordAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPasswordListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(binding.list) {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = passwordAdapter
            passwordAdapter.onItemClick = {
                sharedViewModel.passwordUiModel = it
                findNavController().navigate(R.id.action_passwordListFragment_to_passwordDetailFragment)
            }
        }

        viewModel.liveData.observe(viewLifecycleOwner, { it ->
            passwordAdapter.source = it.map { it.toPasswordUiModel() }
            passwordAdapter.notifyDataSetChanged()

        })

        viewModel.getPasswordList()

        binding.menuItemAddPassword.setOnClickListener {
            findNavController().navigate(R.id.action_passwordListFragment_to_addPasswordFragment)
        }
    }
}
