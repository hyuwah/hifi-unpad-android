package unpad.fmipa.hifi.android.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.calendar_event_item_view.view.*
import unpad.fmipa.hifi.android.R
import unpad.fmipa.hifi.android.ui.model.CalendarEvent

class CalendarEventsAdapter(val onClick: (CalendarEvent) -> Unit) :
    RecyclerView.Adapter<CalendarEventsAdapter.ViewHolder>() {

    val events = mutableListOf<CalendarEvent>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.calendar_event_item_view,parent,false))


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            itemView.setOnClickListener {
                onClick(events[adapterPosition])
            }
        }

        fun bind(event: CalendarEvent) = with(itemView) {
            itemEventText.text = event.text
        }
    }

}