package unpad.fmipa.hifi.android


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_hifi_main_menu.*

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
        rv_main_menu_himpunan.adapter = HimpunanMainMenuAdapter(arrayListOf(
            HimpunanMainMenuModel("Profil HIFI", R.drawable.ic_launcher_background),
            HimpunanMainMenuModel("Orientasi", R.drawable.ic_launcher_background),
            HimpunanMainMenuModel("Badan Pengurus", R.drawable.ic_launcher_background),
            HimpunanMainMenuModel("Dewan", R.drawable.ic_launcher_background),
            HimpunanMainMenuModel("AD/ART", R.drawable.ic_launcher_background)
        ))
    }


}
