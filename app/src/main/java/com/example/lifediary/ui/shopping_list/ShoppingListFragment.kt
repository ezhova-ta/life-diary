package com.example.lifediary.ui.shopping_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.R
import com.example.lifediary.adapters.ListItemClickListener
import com.example.lifediary.adapters.ShoppingListAdapter
import com.example.lifediary.databinding.FragmentShoppingListBinding
import com.example.lifediary.ui.BaseFragment

class ShoppingListFragment : BaseFragment() {
    override val viewModel: ShoppingListViewModel by viewModels()
    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun getInstance(): Fragment {
            return ShoppingListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupAddShoppingListItemInputView()
        setupShoppingListRecycler()
        setupClearShoppingListConfirmationDialog()
        return binding.root
    }

    private fun setupAddShoppingListItemInputView() {
        binding.addShoppingListItemInput.imeOptions = EditorInfo.IME_ACTION_DONE

        binding.addShoppingListItemInput.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.onAddShoppingListItemInputDone()
                return@setOnEditorActionListener true
            }

            return@setOnEditorActionListener false
        }
    }

    private fun setupShoppingListRecycler() {
        val shoppingListAdapter = ShoppingListAdapter(
            ListItemClickListener { viewModel.onShoppingListItemClick(it) },
            ListItemClickListener { viewModel.onHighPriorityShoppingListItemClick(it) },
            ListItemClickListener { viewModel.onDeleteShoppingListItemClick(it) }
        )
        binding.shoppingListView.adapter = shoppingListAdapter
        viewModel.shoppingList.observe(viewLifecycleOwner) { shoppingList ->
            shoppingListAdapter.submitList(shoppingList)
        }
    }

    private fun setupClearShoppingListConfirmationDialog() {
        viewModel.showClearShoppingListConfirmationDialog.observe(viewLifecycleOwner) { needToShow ->
            if(needToShow) showClearShoppingListConfirmationDialog()
        }
    }

    private fun showClearShoppingListConfirmationDialog() {
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
            resources.getColor(R.color.app_blue, requireContext().theme)
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