package com.example.mytodolist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.R
import com.example.mytodolist.databinding.FragmentListBinding


class ListFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[ListFragmentViewModel::class.java]
    }

    private lateinit var adapter: TodoListAdapter
    private var isOnePaneMode: Boolean = false

    private var _binding: FragmentListBinding? = null
    private val binding: FragmentListBinding
        get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    private fun parseArgs() {
        val args = requireArguments()
        isOnePaneMode = args.getBoolean(IS_ONE_PANE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        setOnFloatingActionButtonClickListener()
    }

    private fun setOnFloatingActionButtonClickListener() {
        val container = if (isOnePaneMode) {
            R.id.fragmentContainerViewMain
        } else R.id.fragmentContainerViewToDoItem
        binding.floatingActionButton.setOnClickListener {
            launchFragment(TodoItemFragment.newInstanceAddMode(), container)
        }
    }

    private fun setupRecycleView() {
        adapter = TodoListAdapter()
        setupOnclickListener()
        setupOnLongClickListener()
        setupOnSwipeListener()
        binding.recyclerView.adapter = adapter
        viewModel.todoList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setupOnSwipeListener() {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val todoItem = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteTodoItem(todoItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun setupOnclickListener() {
        val container = if (isOnePaneMode) {
            R.id.fragmentContainerViewMain
        } else R.id.fragmentContainerViewToDoItem
        adapter.onClickListener = {
            launchFragment(TodoItemFragment.newInstanceEditMode(it.id), container)
        }
    }

    private fun setupOnLongClickListener() {
        adapter.onLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    private fun launchFragment(fragment: Fragment, containerId: Int) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {

        const val NAME_OF_FRAGMENT = "ListFragment"
        private const val IS_ONE_PANE = "isOnePane"

        @JvmStatic
        fun newInstance(isOnePainMode: Boolean) = ListFragment().apply {
            arguments = Bundle().apply {
                putBoolean(IS_ONE_PANE, isOnePainMode)
            }
        }

    }
}