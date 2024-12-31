package com.freelab.tech.shootdown.utils

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.freelab.tech.shootdown.R

fun Activity.showToastMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToastMessage(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.generatePlaneImage(): ImageView {
    val screenHeight = resources.displayMetrics.heightPixels
    val randomY = (-100..screenHeight - 250).random()
    val planeImage = ImageView(requireContext()).apply {
        layoutParams = ConstraintLayout.LayoutParams(300, 200).apply {
            endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            topMargin = randomY
            marginEnd = -200
        }

        setImageResource(R.drawable.ic_level_2_plane)
        id = View.generateViewId()
    }

    return planeImage
}

fun Fragment.isColliding(img1: ImageView?, img2: ImageView?): Boolean {
    if (img1 == null || img2 == null) {
        return false
    }
    val rect1 = Rect()
    val rect2 = Rect()

    img1.getGlobalVisibleRect(rect1)
    img2.getGlobalVisibleRect(rect2)

    return Rect.intersects(rect1, rect2)
}

object UIUtils {

}