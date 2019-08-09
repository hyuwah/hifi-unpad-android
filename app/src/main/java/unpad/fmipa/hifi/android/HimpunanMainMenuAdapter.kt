package unpad.fmipa.hifi.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_himpunan_main_menu_list.view.*
import unpad.fmipa.hifi.android.HimpunanMainMenuAdapter.*

class HimpunanMainMenuAdapter(var himpunanMenuList : ArrayList<HimpunanMainMenuModel>) : RecyclerView.Adapter<ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_himpunan_main_menu_list, parent, false))

    override fun getItemCount(): Int = himpunanMenuList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(himpunanMenuList[position])
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        fun bind(item: HimpunanMainMenuModel) = with(itemView) {
            itemView.iv_himpunan_menu.setImageResource(item.image)
            itemView.tv_himpunan_menu_title.text = item.title
        }
    }
}