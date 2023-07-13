package com.example.mytodolist.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mytodolist.R
import com.example.mytodolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TodoItemFragment.OnEditingFinished {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        launchFragment()
    }

    private fun launchFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                binding.fragmentContainerViewMain.id,
                ListFragment.newInstance(isOnePaneMode())
            )
            .addToBackStack(ListFragment.NAME_OF_FRAGMENT)
            .commit()
    }

    private fun isOnePaneMode() = binding.fragmentContainerViewToDoItem == null

    override fun onEditingFinished() {
        Toast.makeText(this, R.string.success, Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack(
            ListFragment.NAME_OF_FRAGMENT, 0
        )
    }
}