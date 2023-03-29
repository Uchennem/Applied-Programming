package com.example.expensetracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.room.Room
import com.example.expensetracker.databinding.ActivityAddExpenseBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddExpenseActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityAddExpenseBinding
    private lateinit var db: AppDatabase // declare global variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Room.databaseBuilder(this, AppDatabase::class.java, "expenses").build() // initialize db instance

        // add text changed listeners
        binding.labelInput.addTextChangedListener {
            if (it!!.count() > 0) {
                binding.labelLayout.error = null
            }
        }

        binding.amountInput.addTextChangedListener {
            if (it!!.count() > 0) {
                binding.amountLayout.error = null
            }
        }

        // add expense button click listener
        binding.addExpenseBtn.setOnClickListener {
            val label = binding.labelInput.text.toString()
            val amount = binding.amountInput.text.toString().toDoubleOrNull()
            val description = binding.descriptionInput.text.toString()

            if (label.isEmpty())
                binding.labelLayout.error = "Please enter a valid label"
            else if (amount == null)
                binding.amountLayout.error = "Please enter a valid amount"
            else {
                val expenses = Expenses(0, label, amount, description)
                insert(expenses)
            }
        }

        // add close button click listener
        binding.closeBtn.setOnClickListener {
            finish()
        }
    }

    private fun insert(expenses: Expenses) {
        GlobalScope.launch {
            db.expensesDAO().insertAll(expenses) // use db instance to insert expenses data
            finish()
        }
    }
}


