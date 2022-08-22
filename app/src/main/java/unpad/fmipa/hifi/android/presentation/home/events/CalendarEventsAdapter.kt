package unpad.fmipa.hifi.android.presentation.home.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import unpad.fmipa.hifi.android.databinding.CalendarEventItemViewBinding
import unpad.fmipa.hifi.android.domain.model.CalendarEvent

class CalendarEventsAdapter(private val onClick: (CalendarEvent) -> Unit) :
    RecyclerView.Adapter<CalendarEventsAdapter.ViewHolder>() {

    val events = mutableListOf<CalendarEvent>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            CalendarEventItemViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(events[position], onClick)
    }

    override fun getItemCount(): Int = events.size

    inner class ViewHolder(
        private val binding: CalendarEventItemViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(event: CalendarEvent, onClick: (CalendarEvent) -> Unit) = with(binding) {
            root.setOnClickListener {
                onClick(event)
            }
            itemEventText.text = event.text
        }
    }

}