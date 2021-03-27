package ca.sog.SOG

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.sog.SOG.DataClass.ChainPerRoot
import ca.sog.SOG.DataClass.ExpirationDate
import java.util.*

class ExpirationDateAdapter(var expirationDates: List<ExpirationDate>, val itemClickListener: OnExpirationDateClickListener)
    : RecyclerView.Adapter<ExpirationDateAdapter.ExpirationDateViewHolder>() {

    inner class ExpirationDateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expirationDate: TextView
        val chainPerRoot: TextView

        init {
            expirationDate = itemView.findViewById(R.id.expirationDate)
            chainPerRoot = itemView.findViewById(R.id.chainPerRoot)
        }

        fun bind(expiryDate: Date, chainPerRoot: Array<ChainPerRoot>, clickListener: OnExpirationDateClickListener){
            itemView.setOnClickListener{
                clickListener.onItemClicked(expiryDate, chainPerRoot)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpirationDateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expiration_date_item, parent, false)
        return ExpirationDateViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpirationDateViewHolder, position: Int) {

        val date = expirationDates[position].expiryDate
        val chain = expirationDates[position].chainPerRoot
        holder.expirationDate.text = date.toString()
        holder.bind(date, chain, itemClickListener)       //have a dedicated button
    }

    override fun getItemCount(): Int {
        return expirationDates.size
    }

}

interface OnExpirationDateClickListener{
    fun onItemClicked(expiryDate: Date, chainPerRoot: Array<ChainPerRoot>)
}