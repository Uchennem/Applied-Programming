package com.example.expensetracker

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.expensetracker.databinding.ActivityMainBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var deletedExpense: Expenses
    private lateinit var expenses :List<Expenses>
    private lateinit var oldExpenses :List<Expenses>
    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase
    private lateinit var piechart: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        expenses = arrayListOf()

        piechart = binding.activityMainPiechart

        expenseAdapter = ExpenseAdapter(expenses)
        linearLayoutManager = LinearLayoutManager(this)

        db = Room.databaseBuilder(this, AppDatabase::class.java, "expenses").build()

        binding.recyclerview.apply {
            adapter = expenseAdapter
            layoutManager = linearLayoutManager
        }

        setupPieChart();
        updatePiechartData();

        // item to remove
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    deleteTransaction(expenses[viewHolder.adapterPosition])
            }

        }

        val swipeHelper = ItemTouchHelper(itemTouchHelper)
        swipeHelper.attachToRecyclerView(binding.recyclerview)

        binding.addBtn.setOnClickListener {
            val intent = Intent(this, AddExpenseActivity2::class.java)
            startActivity(intent)
        }
    }

    private fun fetchAll() {
        GlobalScope.launch {
            expenses = db.expensesDAO().getAll()

            runOnUiThread {
                updateDashboard()
                updatePiechartData()
                expenseAdapter.setData(expenses)
            }
        }
    }

    private fun updateDashboard(){
        val totalAmount = expenses.map{it.amount}.sum()
        val budgetAmount = expenses.filter{it.amount>0}.map{it.amount}.sum()
        val expenseAmount = totalAmount - budgetAmount

        binding.balance.text = "$ %.2f".format(totalAmount)
        binding.budget.text = "$ %.2f".format(budgetAmount)
        binding.expense.text = "$ %.2f".format(expenseAmount)
    }

    private  fun setupPieChart() {
        piechart.setDrawHoleEnabled(true)
        piechart.setUsePercentValues(true)
        piechart.setEntryLabelTextSize(12f)
        piechart.setEntryLabelColor(getColor(R.color.white))
        piechart.setCenterText("Category")
        piechart.setCenterTextSize(24f)
        piechart.description.isEnabled = false

        val legend = piechart.legend
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        legend.orientation = Legend.LegendOrientation.VERTICAL
        legend.setDrawInside(false)
        legend.textColor = getColor(R.color.income)
        legend.isEnabled = true
    }

    private fun updatePiechartData(){
        val totalAmount = expenses.map{it.amount}.sum()
        val budgetAmount = expenses.filter{it.amount>0}.map{it.amount}.sum()

        var spent_percent = (Math.abs(totalAmount)/budgetAmount).toFloat() * 1f
        var budget_percent = 1f - spent_percent

        if (budget_percent <= 0) {
            spent_percent = 1f
            budget_percent = 0f
        }


        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(spent_percent, "budget left"))
        entries.add(PieEntry(budget_percent, "spent amount"))

        val colors = ArrayList<Int>()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }

        val dataSet = PieDataSet(entries, "Expense Category")
        dataSet.setColors(colors)

        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(piechart))
        data.setValueTextSize(12f)

        piechart.setData(data)
        piechart.invalidate()
        piechart.animateY(1400, Easing.EaseInOutQuad)
    }

    private fun deleteTransaction(expense: Expenses) {
        deletedExpense = expense
        oldExpenses = expenses

        GlobalScope.launch {
            db.expensesDAO().delete(expense)

            expenses = expenses.filter { it.id != expense.id }
            runOnUiThread {
                updateDashboard()
                updatePiechartData()
            }
        }
    }
    override fun onResume() {
        super.onResume()
        fetchAll()
    }
}