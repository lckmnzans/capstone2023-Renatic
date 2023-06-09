package com.renatic.app.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.renatic.app.databinding.ItemViewBinding

class PatientsAdapter(private val listPatients: ArrayList<Patients>): RecyclerView.Adapter<PatientsAdapter.ViewHolder>() {
    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClicked(item: Patients)
    }

    class ViewHolder(private val binding: ItemViewBinding): RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.tvName
        val tvSex = binding.tvSex
        val tvDob = binding.tvDob
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemViewBinding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listPatients.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (name, dob, sex) = listPatients[position]
        holder.tvName.text = name
        if (sex == 1) {
            holder.tvSex.text = "Laki-laki"
        } else {
            holder.tvSex.text = "Perempuan"
        }
        holder.tvDob.text = dob.toString()

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClicked(listPatients[holder.adapterPosition])
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}