package me.devpsys.apps.gads2020.aadpp.app.adatpters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import me.devpsys.apps.gads2020.aadpp.app.databinding.RecordRecyclerViewItemBinding
import me.devpsys.apps.gads2020.aadpp.app.models.Record

class RecordsAdapter : RecyclerView.Adapter<RecordsAdapter.RecordViewHolder>() {

    var records = emptyList<Record>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder =
        RecordViewHolder(
            RecordRecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context)).root
        )

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(records[position])
    }

    override fun getItemCount(): Int = records.size

    fun records(records: List<Record>) {
        this.records = records

        notifyDataSetChanged()
    }

    inner class RecordViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(record: Record) {

        }
    }

}