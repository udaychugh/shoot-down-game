package com.freelab.tech.shootdown.navigation

import android.content.Context
import com.freelab.tech.shootdown.game.GameScreenActivity

object NavManager {

    fun gotoGameScreenActivity(context: Context) {
        val intent = GameScreenActivity.getStartIntent(context)
        context.startActivity(intent)
    }

}