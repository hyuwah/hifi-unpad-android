package unpad.fmipa.hifi.android.presentation.home


import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import unpad.fmipa.hifi.android.R
import unpad.fmipa.hifi.android.databinding.FragmentHifiMainMenuBinding
import unpad.fmipa.hifi.android.presentation.model.HimpunanMainMenu

class HimpunanMainMenuFragment : Fragment(R.layout.fragment_hifi_main_menu) {

    private val binding : FragmentHifiMainMenuBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMainMenuHimpunan.adapter = HimpunanMainMenuAdapter(
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
            )
        )
    }


}
