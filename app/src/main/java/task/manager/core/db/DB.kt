package task.manager.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Data::class], version = 3)
abstract class DB : RoomDatabase() {

    abstract fun dao(): Dao


    companion object {
        private var INSTANSE: DB? = null

        fun init(context: Context) {
            if (INSTANSE == null)
                INSTANSE = Room.databaseBuilder(
                    context,
                    DB::class.java,
                    "data"
                )
                    .allowMainThreadQueries()
                    .build()
        }

        fun getDB(): DB = INSTANSE!!
    }

}