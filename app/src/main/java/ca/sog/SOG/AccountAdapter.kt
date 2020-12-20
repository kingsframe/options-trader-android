package ca.sog.SOG

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AccountAdapter(var accounts: List<QuestAccount>, val itemClickListener: OnItemClickListener): RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    inner class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView

        init {
            textView = itemView.findViewById(R.id.accountItem)
        }

        fun bind(accountNumber: String, clickListener: OnItemClickListener){
            itemView.setOnClickListener{
                clickListener.onItemClicked(accountNumber)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.account_item, parent, false)
        return AccountViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.textView.text = accounts[position].number
        holder.bind(accounts[position].number, itemClickListener)
    }

    override fun getItemCount(): Int {
        return accounts.size
    }
}

interface OnItemClickListener{
    fun onItemClicked(accountNumber: String)
}