package com.example.expensetracker

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface ExpensesDAO {
    @Query("SELECT * from expenses")
    fun getAll(): List<Expenses>

    @Insert
    fun insertAll(expenses: Expenses)

    @Delete
    fun delete(expenses: Expenses)

    @Update
    fun update(expenses: Expenses)

}