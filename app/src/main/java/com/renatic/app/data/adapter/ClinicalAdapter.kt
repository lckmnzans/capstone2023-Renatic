package com.renatic.app.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.renatic.app.data.Patients
import com.renatic.app.databinding.ItemViewBinding
import com.renatic.app.data.response.DataItem

class ClinicalAdapter(private val listClinical: ArrayList<DataItem>): RecyclerView.Adapter<ClinicalAdapter.ViewHolder>() {
    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClicked(id: String)
    }

    class ViewHolder(private val binding: ItemViewBinding): RecyclerView.ViewHolder(binding.root) {
        val tv1 = binding.tv1
        val tv2 = binding.tv2
        val tv3 = binding.tv3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemViewBinding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listClinical.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv1.text = "Pemeriksaan ke-".plus("${itemCount-position}")
        holder.tv2.visibility = View.GONE
        holder.tv3.visibility = View.GONE

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClicked(listClinical[holder.adapterPosition].idSkrining.toString())
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}