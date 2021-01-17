package ca.sog.SOG

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PositionsAdapter(var positions: List<Positions>, positionsActivity: PositionsActivity): RecyclerView.Adapter<PositionsAdapter.PositionsViewHolder>() {
    // TODO: Decide on TextView placement for each Positions attribute
    inner class PositionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sym: TextView
        val symId: TextView
        //val openQuantity: TextView
        //val currentMarketValue: TextView
        //val currentPrice: TextView
        //val averageEntryPrice: TextView
        //val closedPnl: TextView
        //val openPnl: TextView

        init {
            sym = itemView.findViewById(R.id.symbol)
            symId = itemView.findViewById(R.id.symbolId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.position_item, parent, false)
        return PositionsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PositionsViewHolder, position: Int) {
        holder.sym.text = positions[position].symbol
        holder.symId.text = positions[position].symbolId.toString()
    }

    override fun getItemCount(): Int {
        return positions.size
    }
}