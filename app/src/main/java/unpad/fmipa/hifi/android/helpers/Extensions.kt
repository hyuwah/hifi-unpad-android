package unpad.fmipa.hifi.android.helpers

import android.content.Context
import android.util.TypedValue
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

fun dpToPx(dp: Int, context: Context): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
        context.resources.displayMetrics
    ).toInt()

internal fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)
