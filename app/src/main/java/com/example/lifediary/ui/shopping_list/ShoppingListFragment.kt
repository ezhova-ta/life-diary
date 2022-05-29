package com.example.lifediary.ui.shopping_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lifediary.R
import com.example.lifediary.adapters.ListItemClickListener
import com.example.lifediary.adapters.ShoppingListAdapter
import com.example.lifediary.data.domain.ShoppingListSortMethodDropDownItem
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
        setupViews()
        return binding.root
    }

    private fun setupViews() {
        setupAddShoppingListItemInputView()
        setupShoppingListRecycler()
        setupClearShoppingListConfirmationDialog()
        setupSortMethodDropDown()
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
            onHighPriorityClickListener = ListItemClickListener { viewModel.onHighPriorityShoppingListItemClick(it) },
            onDeleteItemClickListener = ListItemClickListener { viewModel.onDeleteShoppingListItemClick(it) },
            onItemClickListener = ListItemClickListener { viewModel.onShoppingListItemClick(it) },
            onItemLongClickListener = ListItemClickListener { viewModel.onShoppingListItemLongClick(it) }
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
        showConfirmationDialog(
            messageRes = R.string.clear_shopping_list_confirmation,
            positiveButtonTextRes = R.string.clear,
            negativeButtonRes = R.string.cancel,
            onConfirmed = viewModel::onClearShoppingListConfirmed,
            onCancelled = viewModel::onClearShoppingListCancelled
        )
    }

    private fun setupSortMethodDropDown() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.sort_method_spinner_item,
            ShoppingListSortMethodDropDownItem.getAllStrings(requireContext())
        )

        binding.sortMethodDropDown.adapter = adapter

        binding.sortMethodDropDown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val sortMethod = ShoppingListSortMethodDropDownItem.getFromPosition(position)
                viewModel.onSortMethodSelected(sortMethod)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        viewModel.shoppingListSortMethodId.observe(viewLifecycleOwner) { sortMethodId ->
            sortMethodId ?: return@observe
            val position = ShoppingListSortMethodDropDownItem.getPositionFromId(sortMethodId)
            binding.sortMethodDropDown.setSelection(position)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}