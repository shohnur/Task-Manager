package task.manager.core.db

import androidx.room.*
import androidx.room.Dao

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: Data)

    @Query("select * from data")
    fun getData(): List<Data>?

    @Delete
    fun deleteTask(data: Data)

    @Update
    fun updateStatus(data: Data)

}