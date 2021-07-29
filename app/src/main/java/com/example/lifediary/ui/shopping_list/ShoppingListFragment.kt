package com.example.lifediary.ui.shopping_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.example.lifediary.R
import com.example.lifediary.adapters.OnShoppingListItemClickListener
import com.example.lifediary.adapters.ShoppingListAdapter
import com.example.lifediary.databinding.FragmentShoppingListBinding
import com.example.lifediary.ui.BaseFragment

class ShoppingListFragment : BaseFragment() {
    private val viewModel: ShoppingListViewModel by viewModels()
    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupShoppingListRecycler()
        setupClearShoppingListConfirmationDialog()
        return binding.root
    }

    private fun setupShoppingListRecycler() {
        val shoppingListAdapter = ShoppingListAdapter(
            OnShoppingListItemClickListener { viewModel.onShoppingListItemClick(it) },
            OnShoppingListItemClickListener { viewModel.onHighPriorityShoppingListItemClick(it) },
            OnShoppingListItemClickListener { viewModel.onDeleteShoppingListItemClick(it) }
        )
        binding.shoppingListView.adapter = shoppingListAdapter
        viewModel.shoppingList.observe(viewLifecycleOwner) { shoppingList ->
            shoppingListAdapter.submitList(shoppingList)
        }
    }

    private fun setupClearShoppingListConfirmationDialog() {
        viewModel.showClearShoppingListConfirmationDialog.observe(viewLifecycleOwner) { needToShow ->
            if(needToShow) showClearShoppingListDialog()
        }
    }

    private fun showClearShoppingListDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.clear_shopping_list_confirmation)
            .setPositiveButton(R.string.clear) { _, _ ->
                viewModel.onClearShoppingListConfirmed()
            }
            .setNegativeButton(R.string.cancel) { _, _ ->
                viewModel.onClearShoppingListCancelled()
            }
            .setCancelable(false)
            .show()
            .setButtonsStyle()
    }

    private fun AlertDialog.setButtonsStyle() {
        getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(
            resources.getColor(R.color.color_link, requireContext().theme)
        )

        getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(
            resources.getColor(R.color.black_opacity_50, requireContext().theme)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}