package task.manager.core.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import task.manager.R
import task.manager.core.db.Data
import task.manager.core.utils.Constants
import task.manager.databinding.LayoutDialogBinding
import java.util.*

class AddTaskDialog(context: Context, titleD: String = "Add new task", data: Data? = null) :
    AlertDialog(context) {

    private var date = ""
    private var binding: LayoutDialogBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context), R.layout.layout_dialog, null, false
    )

    var listener: ((data: Data) -> Unit)? = null

    init {

        binding.apply {

            title.text = titleD

            data?.let {
                name.setText(it.taskName)
                date.text = it.date
                this@AddTaskDialog.date = it.date
                add.text = "Update"
            }

            date.setOnClickListener {
                pickDateTime()
            }

            add.setOnClickListener {
                if (name.text.isBlank()) name.error = "This field must be filled"
                else {
                    if (this@AddTaskDialog.date == "") Toast.makeText(
                        context,
                        "You need to choose date",
                        Toast.LENGTH_SHORT
                    ).show()
                    else {
                        listener?.invoke(
                            Data(
                                data?.id ?: 0,
                                name.text.toString(),
                                this@AddTaskDialog.date,
                                Constants.NEW
                            )
                        )
                        cancel()
                    }
                }
            }

            setView(root)
        }
    }

    private fun pickDateTime() {
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)

        DatePickerDialog(context, { _, year, month, day ->
            TimePickerDialog(context, { _, hour, minute ->
                date = "$day/$month/$year $hour:$minute"
                binding.date.text = date
            }, startHour, startMinute, false).show()
        }, startYear, startMonth, startDay).show()
    }

}