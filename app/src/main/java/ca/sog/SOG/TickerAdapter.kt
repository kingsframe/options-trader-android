package ca.sog.SOG

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TickerAdapter(var tickers: List<Ticker>, val itemClickListener: OnItemClickListener): RecyclerView.Adapter<TickerAdapter.TickerViewHolder>() {

    inner class TickerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val symbol: TextView
        val description: TextView
        val listingExchange: TextView
        init {
            symbol = itemView.findViewById(R.id.symbol)
            description = itemView.findViewById(R.id.description)
            listingExchange = itemView.findViewById(R.id.listingExchange)
        }

        fun bind(symbolId: Int, symbolName: String, clickListener: OnItemClickListener){
            itemView.setOnClickListener{
                clickListener.onItemClicked(symbolId, symbolName)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TickerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ticker_item, parent, false)
        return TickerViewHolder(view)
    }

    override fun onBindViewHolder(holder: TickerViewHolder, position: Int) {
        holder.symbol.text = tickers[position].symbol
        holder.description.text = tickers[position].description
        holder.listingExchange.text = tickers[position].listingExchange
        holder.bind(tickers[position].symbolId, tickers[position].symbol, itemClickListener)       //have a dedicated button
    }

    override fun getItemCount(): Int {
        return tickers.size
    }
}

interface OnItemClickListener{
    fun onItemClicked(symbolId: Int, symbolName: String)
}