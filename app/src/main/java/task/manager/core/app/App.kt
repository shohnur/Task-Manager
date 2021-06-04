package task.manager.core.app

import android.app.Application
import task.manager.core.db.DB

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DB.init(this)
    }

}