package com.example.testapp.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.entity.Specialist
import com.example.testapp.entity.Specialty

class SpecialtyRecyclerAdapter(
    private var adapterList: List<Any> = mutableListOf(),
    var specialtyClick: ((Int) -> Unit)? = null,
    var specialistClick: ((Int) -> Unit)? = null
) : RecyclerView.Adapter<SpecialtyRecyclerAdapter.BaseViewHolder<*>>() {

    companion object {
        private const val TYPE_SPECIALTY = 0
        private const val TYPE_SPECIALIST = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_SPECIALTY -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recycler_specialty, parent, false)
                SpecialtyViewHolder(view)
            }
            TYPE_SPECIALIST -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recycler_specialist, parent, false)
                SpecialistViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = adapterList[position]
        when (holder) {
            is SpecialtyViewHolder -> {
                holder.bind ((element as Specialty).specialtyName.toString())
                holder.itemView.setOnClickListener{
                    element.specialtyId?.let { id -> specialtyClick?.invoke(id) }
                }
                holder.setTextColor(position)
            }
            is SpecialistViewHolder -> {
                holder.bind(element as Specialist)
                holder.itemView.setOnClickListener{
                    element.id?.let { id -> specialistClick?.invoke(id.toInt()) }
                }
                holder.setTextColor(position)
            }
            else -> throw IllegalArgumentException()
        }
        if(position%2 == 0) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.colorRed))
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.colorWhite))
        }
    }

    override fun getItemCount(): Int {
        return adapterList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (adapterList[position]) {
            is Specialist -> TYPE_SPECIALIST
            is Specialty -> TYPE_SPECIALTY
            else -> throw IllegalArgumentException("Invalid type of data $position")
        }
    }

    fun setItems(specialtyList: List<Any>) {
        this.adapterList = specialtyList
    }

    class SpecialtyViewHolder(itemView: View) : BaseViewHolder<String>(itemView) {
        private var specialtyName: TextView = itemView.findViewById(R.id.specialtyNameTextView)

        override fun bind(item: String) {
            specialtyName.text = item
        }

        fun setTextColor(position: Int){
            if(position%2 == 0) {
                specialtyName.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorWhite))
            } else {
                ContextCompat.getColor(itemView.context, R.color.colorRed)
            }
        }
    }

    class SpecialistViewHolder(itemView: View) : BaseViewHolder<Specialist>(itemView) {
        private var specialistFirstName: TextView = itemView.findViewById(R.id.specialistFirstName)
        private var specialistLastName: TextView = itemView.findViewById(R.id.specialistLastName)
        private var specialistAge: TextView = itemView.findViewById(R.id.specialistAge)

        override fun bind(item: Specialist) {
            specialistFirstName.text = item.firstName
            specialistLastName.text = item.lastName
            if(item.getSpecialistAge() != "-") {
                specialistAge.text = itemView.resources.getQuantityString(R.plurals.specialist_age, item.getSpecialistAge().toInt(), item.getSpecialistAge())
            } else {
                specialistAge.text = item.getSpecialistAge()
            }
        }

        fun setTextColor(position: Int){
            if(position%2 == 0) {
                specialistFirstName.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorWhite))
                specialistLastName.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorWhite))
                specialistAge.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorWhite))
            } else {
                specialistFirstName.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorRed))
                specialistLastName.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorRed))
                specialistAge.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorRed))
            }
        }
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }
}