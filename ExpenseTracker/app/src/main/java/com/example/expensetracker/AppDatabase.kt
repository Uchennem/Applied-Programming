package com.example.expensetracker

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Expenses::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expensesDAO() : ExpensesDAO
}