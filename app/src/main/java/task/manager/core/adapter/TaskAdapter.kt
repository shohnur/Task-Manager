package task.manager.core.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import task.manager.R
import task.manager.core.db.Data
import task.manager.core.utils.Constants
import task.manager.databinding.ItemTaskBinding

class TaskAdapter(var context:Context) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    private val data = arrayListOf<Data>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(d: List<Data>) {
        data.clear()
        data.addAll(d)
        notifyDataSetChanged()
    }


    fun delete(d: Data) {
        val removingId = data.indexOf(d)
        data.remove(d)
        notifyItemRemoved(removingId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_task,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindData(data[position])

    override fun getItemCount(): Int = data.size

    var deleteClicked: ((data: Data) -> Unit)? = null
    var rootClicked: ((data: Data) -> Unit)? = null

    inner class ViewHolder(var binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(data: Data) {
            binding.apply {
                name.text = data.taskName
                date.text = data.date
                delete.setOnClickListener {
                    deleteClicked?.invoke(data)
                }

                when(data.status){
                    Constants.NEW->{ card.setCardBackgroundColor(context.getColor(R.color.all)) }
                    Constants.PROCESS->{ card.setCardBackgroundColor(context.getColor(R.color.process)) }
                    Constants.DONE->{ card.setCardBackgroundColor(context.getColor(R.color.done)) }
                }

                root.setOnClickListener {
                    rootClicked?.invoke(data)
                }
            }
        }

    }
}