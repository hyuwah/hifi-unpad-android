package unpad.fmipa.hifi.android.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import unpad.fmipa.hifi.android.databinding.ItemHimpunanMainMenuListBinding
import unpad.fmipa.hifi.android.presentation.home.HimpunanMainMenuAdapter.ViewHolder
import unpad.fmipa.hifi.android.presentation.model.HimpunanMainMenu

class HimpunanMainMenuAdapter(var himpunanMenuList: ArrayList<HimpunanMainMenu>) :
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemHimpunanMainMenuListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount(): Int = himpunanMenuList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(himpunanMenuList[position])
    }

    inner class ViewHolder(private val binding: ItemHimpunanMainMenuListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HimpunanMainMenu) = with(binding) {
            ivHimpunanMenu.setImageResource(item.image)
            tvHimpunanMenuTitle.text = item.title
        }
    }
}