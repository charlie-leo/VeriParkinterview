package com.veripark.interview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.veripark.interview.databinding.HistoryItemBinding

class HistoryAdapter(private var arrayList: ArrayList<Pair<Int, Double>>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewModel>() {

    /**
     * Method use to change the recyclerview list data.
     */
    fun setArrayList(arrayList: ArrayList<Pair<Int, Double>>) {
        this.arrayList = arrayList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewModel {
        val historyItemBinding: HistoryItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.history_item,
            parent,
            false
        )
        return HistoryViewModel(historyItemBinding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: HistoryViewModel, position: Int) {
        var neg = 0
        if (position < arrayList.size - 1) {
            neg = arrayList[position + 1].first
        }

        holder.onBind(arrayList[position], neg)
    }

    class HistoryViewModel(private val historyItemBinding: HistoryItemBinding) :
        RecyclerView.ViewHolder(historyItemBinding.root) {

        /**
         * Method use to populate the data to the UI.
         */
        fun onBind(pair: Pair<Int, Double>, neg: Int) {
            val reading = if (neg > 0) {
                pair.first - neg
            } else {
                pair.first
            }
            historyItemBinding.meterReading.text = reading.toString()
            historyItemBinding.billAmount.text = pair.second.toString()
        }
    }
}