package unpad.fmipa.hifi.android.presentation.home.himpunan

import android.view.View
import android.view.ViewParent
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import unpad.fmipa.hifi.android.R
import unpad.fmipa.hifi.android.databinding.FragmentHifiMainMenuBinding

@EpoxyModelClass(layout = R.layout.fragment_hifi_main_menu)
abstract class HimpunanMenuModel : EpoxyModelWithHolder<HimpunanMenuModel.Holder>() {

    companion object {
        const val ID = "HimpunanMenuModel"
    }

    @EpoxyAttribute(EpoxyAttribute.Option.DoNotHash)
    open var itemClickListener: ((HimpunanMainMenu) -> Unit)? = null

    override fun getDefaultLayout() = R.layout.fragment_hifi_main_menu

    override fun createNewHolder(parent: ViewParent) = Holder()

    override fun bind(holder: Holder) {
        super.bind(holder)
        with(holder.binding) {
            rvMainMenuHimpunan.adapter = HimpunanMainMenuAdapter(
                arrayListOf(
                    HimpunanMainMenu(
                        "Profil HIFI",
                        R.drawable.ic_launcher_background
                    ),
                    HimpunanMainMenu(
                        "Orientasi",
                        R.drawable.ic_launcher_background
                    ),
                    HimpunanMainMenu(
                        "Badan Pengurus",
                        R.drawable.ic_launcher_background
                    ),
                    HimpunanMainMenu(
                        "Dewan",
                        R.drawable.ic_launcher_background
                    ),
                    HimpunanMainMenu(
                        "AD/ART",
                        R.drawable.ic_launcher_background
                    )
                ),
                itemClickListener
            )
        }
    }

    override fun unbind(holder: Holder) {
        super.unbind(holder)
        holder.binding.rvMainMenuHimpunan.adapter = null
    }

    class Holder : EpoxyHolder() {
        lateinit var binding: FragmentHifiMainMenuBinding
            private set

        override fun bindView(itemView: View) {
            binding = FragmentHifiMainMenuBinding.bind(itemView)
        }
    }

}