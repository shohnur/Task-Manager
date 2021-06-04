package task.manager.core.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Data(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "taskName")
    val taskName: String,
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "status")
    var status: String
)