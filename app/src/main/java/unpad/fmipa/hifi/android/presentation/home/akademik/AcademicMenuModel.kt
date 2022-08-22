package unpad.fmipa.hifi.android.presentation.home.akademik

import android.net.Uri
import android.view.View
import android.view.ViewParent
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import unpad.fmipa.hifi.android.R
import unpad.fmipa.hifi.android.databinding.ViewAcademicMenuBinding
import unpad.fmipa.hifi.android.helpers.ChromeCustomTabs

@EpoxyModelClass(layout = R.layout.view_academic_menu)
abstract class AcademicMenuModel: EpoxyModelWithHolder<AcademicMenuModel.Holder>() {

    companion object {
        const val ID = "AcademicMenuModel"
    }

    override fun getDefaultLayout() = R.layout.view_academic_menu

    override fun createNewHolder(parent: ViewParent) = Holder()
    
    override fun bind(holder: Holder) {
        super.bind(holder)
        with(holder.binding) {
            btnMainPaus.setOnClickListener {
                ChromeCustomTabs.create(root.context).build()
                    .launchUrl(root.context, Uri.parse(ChromeCustomTabs.PAUS_URL))
            }

            btnMainAngkutan.setOnClickListener {
                ChromeCustomTabs.create(root.context).build()
                    .launchUrl(root.context, Uri.parse(ChromeCustomTabs.ANGKUTAN_URL))
            }

            btnMainPintas.setOnClickListener {
                ChromeCustomTabs.create(root.context).build()
                    .launchUrl(root.context, Uri.parse(ChromeCustomTabs.PINTAS_URL))
            }
        }
    }
    
    class Holder: EpoxyHolder() {
        lateinit var binding: ViewAcademicMenuBinding
        override fun bindView(itemView: View) {
            binding = ViewAcademicMenuBinding.bind(itemView)
        }

    }
}