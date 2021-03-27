package ca.sog.SOG

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import ca.sog.SOG.DataClass.ExpirationDate
import java.util.*

class ExpirationDateAdapter(var expirationDates: List<ExpirationDate>, val itemClickListener: OnExpirationDateClickListener)
    : RecyclerView.Adapter<ExpirationDateAdapter.ExpirationDateViewHolder>() {

    inner class ExpirationDateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val expirationDate: TextView

        init {
            expirationDate = itemView.findViewById(R.id.expirationDate)
        }

        fun bind(accountNumber: Date, clickListener: OnExpirationDateClickListener){
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

        //val date =  SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(expirationDates[position].expiryDate.toString()).toString()
        //val formattedDate = SimpleDateFormat("MMM dd, yyyy", Locale.US).format(date)
        //holder.expirationDate.text = formattedDate
        //holder.bind(formattedDate, itemClickListener)       //have a dedicated button

        val date = expirationDates[position].expiryDate
        holder.expirationDate.text = date.toString()
        holder.bind(date, itemClickListener)       //have a dedicated button
    }

    override fun getItemCount(): Int {
        return expirationDates.size
    }

}

interface OnExpirationDateClickListener{
    fun onItemClicked(expiryDate: Date)
}