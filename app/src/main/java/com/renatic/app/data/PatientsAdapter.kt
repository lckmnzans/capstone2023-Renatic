package com.renatic.app.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.renatic.app.R

class PatientsAdapter(private val listPatients: ArrayList<Patients>): RecyclerView.Adapter<PatientsAdapter.ViewHolder>() {
    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClicked(item: Patients)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvDob: TextView = itemView.findViewById(R.id.tv_dob)
        val tvSex: TextView = itemView.findViewById(R.id.tv_sex)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listPatients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (name, dob, sex, _) = listPatients[position]
        holder.tvName.text = name
        holder.tvDob.text = dob
        holder.tvSex.text = sex

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClicked(listPatients[holder.adapterPosition])
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}