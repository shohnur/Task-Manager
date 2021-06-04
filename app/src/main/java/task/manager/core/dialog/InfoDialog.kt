package task.manager.core.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import task.manager.R
import task.manager.core.db.Data
import task.manager.core.utils.Constants
import task.manager.databinding.InfoDialogBinding

class InfoDialog(context: Context, data: Data) : AlertDialog(context) {

    private val binding = DataBindingUtil.inflate<InfoDialogBinding>(
        LayoutInflater.from(context),
        R.layout.info_dialog,
        null, false
    )
    var listener: ((data:Data) -> Unit)? = null

    init {

        binding.apply {
            name.text = data.taskName
            date.text = data.date
            when (data.status) {
                Constants.NEW -> {
                    changeStatus.text = "Begin"
                }
                Constants.PROCESS -> {
                    changeStatus.text = "Finish"
                }
            }

            changeStatus.setOnClickListener {
                when (data.status) {
                    Constants.NEW -> {
                        listener?.invoke(data.apply { status=Constants.PROCESS })
                    }
                    Constants.PROCESS -> {
                        listener?.invoke(data.apply { status=Constants.DONE })
                    }
                }
                cancel()
            }
            setView(root)
        }

    }

}