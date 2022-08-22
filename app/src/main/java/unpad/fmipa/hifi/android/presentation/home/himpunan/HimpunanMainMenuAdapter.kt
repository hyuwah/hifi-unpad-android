package unpad.fmipa.hifi.android.presentation.home.himpunan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import unpad.fmipa.hifi.android.databinding.ItemHimpunanMainMenuListBinding
import unpad.fmipa.hifi.android.presentation.home.himpunan.HimpunanMainMenuAdapter.ViewHolder

class HimpunanMainMenuAdapter(
    var himpunanMenuList: ArrayList<HimpunanMainMenu>,
    val onItemClickListener: ((HimpunanMainMenu) ->Unit)?
) : RecyclerView.Adapter<ViewHolder>() {

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
            root.setOnClickListener {
                onItemClickListener?.invoke(item)
            }
            ivHimpunanMenu.setImageResource(item.image)
            tvHimpunanMenuTitle.text = item.title
        }
    }
}