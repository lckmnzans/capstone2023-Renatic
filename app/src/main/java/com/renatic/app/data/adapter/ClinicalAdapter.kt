package com.renatic.app.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.renatic.app.databinding.ItemViewBinding
import com.renatic.app.data.response.ClinicalItem

class ClinicalAdapter(private val listClinical: ArrayList<ClinicalItem>): RecyclerView.Adapter<ClinicalAdapter.ViewHolder>() {
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
        val (pregnancies, idKlinis, glucose, insulin, patient, skin, diabetesDegree, tanggalLahir, blood, bmi) = listClinical[position]
        holder.tv1.text = glucose.toString()
        holder.tv2.text = insulin.toString()
        holder.tv3.text = skin.toString()
    }
}