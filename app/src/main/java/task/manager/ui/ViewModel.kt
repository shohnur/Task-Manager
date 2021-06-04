package task.manager.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import task.manager.core.db.DB
import task.manager.core.db.Data

class ViewModel : ViewModel() {


    var liveData: MutableLiveData<List<Data>> = MutableLiveData()
    private val dao = DB.getDB().dao()

    fun loadData() {
        dao.getData()?.let {
            liveData.value = it
        }
    }

    fun deleteData(data: Data) {
        dao.deleteTask(data)
        loadData()
    }

    fun addTask(data: Data) {
        dao.insert(data)
        loadData()
    }

    fun update(data: Data) {
        dao.update(data)
        loadData()
    }
}