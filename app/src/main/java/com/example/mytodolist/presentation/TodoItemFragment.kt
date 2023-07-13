package com.example.mytodolist.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.mytodolist.R
import com.example.mytodolist.databinding.FragmentTodoItemBinding
import com.example.mytodolist.domain.entities.Importance
import com.example.mytodolist.domain.entities.TodoItem

private const val ARG_SCREEN_MODE = "param1"
private const val ARG_ITEM_ID = "param2"

class TodoItemFragment : Fragment() {

    private lateinit var onEditingFinished: OnEditingFinished
    private var screenMode: String = MODE_UNKNOWN
    private var todoItemId: Int = TodoItem.UNDEFINED_ID

    private var _binding: FragmentTodoItemBinding? = null
    private val binding: FragmentTodoItemBinding
        get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this)[TodoItemFragmentViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinished) {
            onEditingFinished = context
        } else throw RuntimeException("Activity must implement OnEditingFinished")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArg()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchRightMode()
        observeViewModel()
        addTextChangeListener()
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }

    private fun launchEditMode() {
        viewModel.getTodoItemWithId(todoItemId)
        viewModel.todoItem.observe(viewLifecycleOwner) {
            with(binding) {
                tieTask.setText(it.name)
                when (it.importance) {
                    Importance.LOW -> radioButtonLow.isChecked = true
                    Importance.MEDIUM -> radioButtonMedium.isChecked = true
                    Importance.HIGH -> radioButtonHigh.isChecked = true
                }
            }
        }
        binding.saveButton.setOnClickListener {
            viewModel.editTodoItem(
                binding.tieTask.text.toString(),
                getImportanceFromRadioButtons(binding.radioGroup.checkedRadioButtonId)
            )
        }
    }

    private fun getImportanceFromRadioButtons(id: Int): Importance {
        return when (id) {
            binding.radioButtonLow.id -> Importance.LOW
            binding.radioButtonHigh.id -> Importance.HIGH
            binding.radioButtonMedium.id -> Importance.MEDIUM
            else -> throw RuntimeException("Unknown radioButton id: $id")
        }
    }


    private fun launchAddMode() {
        binding.radioButtonLow.isChecked = true
        binding.saveButton.setOnClickListener {
            viewModel.addTodoItem(
                binding.tieTask.text.toString(),
                getImportanceFromRadioButtons(binding.radioGroup.checkedRadioButtonId)
            )
        }
    }

    private fun observeViewModel() {
        viewModel.errorInputTask.observe(viewLifecycleOwner) {
            val message = if (it) {
                requireActivity().getString(R.string.error_message)
            } else null
            binding.textInputLayout.error = message
        }

        viewModel.readyToClose.observe(viewLifecycleOwner) {
            onEditingFinished.onEditingFinished()
        }
    }

    private fun addTextChangeListener() {
        binding.tieTask.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                viewModel.resetErrorInputTask()
            }
        })
    }

    private fun parseArg() {
        val args = requireArguments()
        if (!args.containsKey(ARG_SCREEN_MODE)) {
            throw RuntimeException("args doesn't contain AGR_SCREEN_MODE")
        }
        val mode = args.getString(ARG_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown mode: $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(ARG_ITEM_ID)) throw RuntimeException("Id is empty")
        }
        todoItemId = args.getInt(ARG_ITEM_ID, TodoItem.UNDEFINED_ID)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface OnEditingFinished {

        fun onEditingFinished()
    }

    companion object {

        private const val MODE_UNKNOWN = "mode_unknown"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"

        @JvmStatic
        fun newInstanceEditMode(id: Int) =
            TodoItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SCREEN_MODE, MODE_EDIT)
                    putInt(ARG_ITEM_ID, id)
                }
            }

        @JvmStatic
        fun newInstanceAddMode() =
            TodoItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SCREEN_MODE, MODE_ADD)
                }
            }
    }
}