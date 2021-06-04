package task.manager.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import task.manager.R
import task.manager.core.adapter.TaskAdapter
import task.manager.core.db.Data
import task.manager.core.dialog.AddTaskDialog
import task.manager.core.dialog.InfoDialog
import task.manager.core.utils.Constants
import task.manager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModel
    private lateinit var adapter: TaskAdapter
    private lateinit var binding: ActivityMainBinding
    private val data = arrayListOf<Data>()
    private var status = Constants.NEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)

        viewModel.loadData()

        initRecycler()

        viewModel.liveData.observe(this, {
            Log.i("RRR", "onCreate: $it")
            data.clear()
            data.addAll(it)
            loadRecyclerData()
        })


        binding.apply {

            bottomNav.apply {
                setItemSelected(R.id.all, true)

                setOnItemSelectedListener { id ->
                    when (id) {
                        R.id.all -> {
                            loadAll()
                        }
                        R.id.inProcess -> {
                            loadInProcess()
                        }
                        R.id.done -> {
                            loadDone()
                        }
                    }
                }
            }

            add.setOnClickListener {
                val dialog = AddTaskDialog(this@MainActivity)
                dialog.listener = { name, date ->
                    Data(0, name, date, Constants.NEW).apply {
                        viewModel.addTask(this)
                    }
                }
                dialog.show()
            }

        }


    }

    private fun loadDone() {
        binding.apply {
            data.filter { it.status == Constants.DONE }.apply {
                if (isEmpty()) empty.visibility = View.VISIBLE else
                    empty.visibility = View.GONE
                adapter.setData(this)
            }
            status = Constants.DONE
            add.visibility = View.GONE
        }
    }

    private fun loadInProcess() {
        binding.apply {
            data.filter { it.status == Constants.PROCESS }.apply {
                if (isEmpty()) empty.visibility = View.VISIBLE else
                    empty.visibility = View.GONE
                adapter.setData(this)
            }
            status = Constants.PROCESS
            add.visibility = View.GONE
        }
    }

    private fun loadAll() {
        binding.apply {
            data.apply {
                if (isEmpty()) empty.visibility = View.VISIBLE else
                    empty.visibility = View.GONE
                adapter.setData(this)
            }
            status = Constants.NEW
            add.visibility = View.VISIBLE
        }
    }

    private fun loadRecyclerData() {
        when (status) {
            Constants.NEW -> {
                loadAll()
            }
            Constants.PROCESS -> {
                loadInProcess()
            }
            Constants.DONE -> {
                loadDone()
            }

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecycler() {

        adapter = TaskAdapter(this)

        binding.recycler.adapter = adapter

        adapter.setData(data)

        adapter.deleteClicked = {
            viewModel.deleteData(it)
            adapter.delete(it)
        }

        adapter.rootClicked = {
            if (it.status != Constants.DONE) {
                val dialog = InfoDialog(this, it)
                dialog.listener = { d ->
                    viewModel.updateStatus(d)
                }
                dialog.show()
            }
        }

    }
}