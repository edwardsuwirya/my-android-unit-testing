package com.enigmacamp.myunittesting.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(vararg obj: T)

    @Update
    fun update(obj: T)

    @Delete
    fun delete(obj: T)
}