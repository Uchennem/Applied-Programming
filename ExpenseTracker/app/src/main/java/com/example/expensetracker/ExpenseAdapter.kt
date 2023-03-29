package com.example.expensetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter (private var transactions: List<Expenses>) :
    RecyclerView.Adapter<ExpenseAdapter.ExpenseHolder>() {
    class ExpenseHolder(view: View) : RecyclerView.ViewHolder(view) {
        val label : TextView = view.findViewById(R.id.label)
        val amount : TextView = view.findViewById(R.id.amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expenses_layout, parent, false)
        return ExpenseHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseHolder, position: Int) {
        val expense = transactions[position]
        val context = holder.amount.context

        holder.label.text = expense.label

        if (expense.amount >= 0) {
            holder.amount.text = "+ $%.2f".format(expense.amount)
            holder.amount.setTextColor(context.getColor(R.color.income))
        } else {
            holder.amount.text = "- $%.2f".format(Math.abs(expense.amount))
            holder.amount.setTextColor(context.getColor(R.color.expense))
        }
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    fun setData(transactions: List<Expenses>){
        this.transactions = transactions
        notifyDataSetChanged()
    }
}