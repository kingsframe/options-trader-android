package ca.sog.SOG

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.sog.SOG.DataClass.ExpirationDate

class ExpirationDateAdapter(var expirationDates: List<ExpirationDate>, val itemClickListener: OnExpirationDateClickListener)
    : RecyclerView.Adapter<ExpirationDateAdapter.ExpirationDateViewHolder>() {

    inner class ExpirationDateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expirationDate: TextView

        init {
            expirationDate = itemView.findViewById(R.id.expirationDate)
        }

        fun bind(accountNumber: String, clickListener: OnExpirationDateClickListener){
            itemView.setOnClickListener{
                clickListener.onItemClicked(accountNumber)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpirationDateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expiration_date_item, parent, false)
        return ExpirationDateViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpirationDateViewHolder, position: Int) {
        holder.expirationDate.text = expirationDates[position].expiryDate
        holder.bind(expirationDates[position].expiryDate, itemClickListener)       //have a dedicated button
    }

    override fun getItemCount(): Int {
        return expirationDates.size
    }

}

interface OnExpirationDateClickListener{
    fun onItemClicked(expiryDate: String)
}