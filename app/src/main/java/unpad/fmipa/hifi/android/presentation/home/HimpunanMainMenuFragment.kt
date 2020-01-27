package unpad.fmipa.hifi.android.presentation.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_hifi_main_menu.*
import unpad.fmipa.hifi.android.R
import unpad.fmipa.hifi.android.presentation.model.HimpunanMainMenu

/**
 * A simple [Fragment] subclass.
 *
 */
class HimpunanMainMenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hifi_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_main_menu_himpunan.adapter = HimpunanMainMenuAdapter(
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
