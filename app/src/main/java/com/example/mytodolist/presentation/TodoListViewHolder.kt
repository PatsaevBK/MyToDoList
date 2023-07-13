package com.example.mytodolist.presentation

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.R

class TodoListViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    val layout = view.findViewById<LinearLayout>(R.id.linearLayoutCardView)
    val name = view.findViewById<TextView>(R.id.textViewName)
}