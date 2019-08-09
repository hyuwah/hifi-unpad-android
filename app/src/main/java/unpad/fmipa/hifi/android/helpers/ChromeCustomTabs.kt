package unpad.fmipa.hifi.android.helpers

import android.content.Context
import android.graphics.BitmapFactory
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import unpad.fmipa.hifi.android.R

object ChromeCustomTabs {

    const val PAUS_URL = "http://paus.unpad.ac.id/"
    const val ANGKUTAN_URL = "https://siat.unpad.ac.id/sarpras/angkutankampus/"
    const val PINTAS_URL = "http://pintas.unpad.ac.id"

    fun create(ctx: Context): CustomTabsIntent.Builder {
        return CustomTabsIntent.Builder().apply {
            var bitmap = BitmapFactory.decodeResource(ctx.resources, R.drawable.ic_arrow_back)
            setToolbarColor(ContextCompat.getColor(ctx, R.color.colorPrimary))
            setShowTitle(true)
            setCloseButtonIcon(bitmap)
            //setExitAnimations(ctx, android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}