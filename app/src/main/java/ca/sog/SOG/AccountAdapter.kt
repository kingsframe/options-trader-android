package ca.sog.SOG

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ca.sog.SOG.DataClass.QuestAccount

class AccountAdapter(var accounts: List<QuestAccount>, val itemClickListener: OnItemClickListener_old)
    : RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    inner class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val accNumber: TextView
        val accType: TextView
        val accStatus: TextView
        init {
            accNumber = itemView.findViewById(R.id.accountNumber)
            accType = itemView.findViewById(R.id.accountType)
            accStatus = itemView.findViewById(R.id.accountStatus)
        }

        fun bind(accountNumber: String, clickListener: OnItemClickListener_old){
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
        holder.accNumber.text = accounts[position].number
        holder.accType.text = accounts[position].type
        holder.accStatus.text = accounts[position].status
        holder.bind(accounts[position].number, itemClickListener)       //have a dedicated button
    }

    override fun getItemCount(): Int {
        return accounts.size
    }
}

interface OnItemClickListener_old{
    fun onItemClicked(accountNumber: String)
}