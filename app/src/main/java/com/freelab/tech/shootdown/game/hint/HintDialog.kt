package com.freelab.tech.shootdown.game.hint

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.freelab.tech.shootdown.databinding.DialogHintBinding

object HintDialog {

    private var dialog: Dialog? = null

    private fun resolvedString(ctx: Context, input: Any): String {
        return when (input) {
            is Int -> ctx.getString(input)
            is String -> input
            else -> throw IllegalArgumentException("Message must be either String or Int")
        }
    }

    fun showHintDialog(ctx: Context, t: Any, m: Any, listener: () -> Unit) {
        if (dialog?.isShowing == true) dialog?.dismiss()

        dialog = Dialog(ctx)

        val layoutInflater = LayoutInflater.from(ctx)
        val db = DialogHintBinding.inflate(layoutInflater)
        dialog?.setContentView(db.root)

        val title = resolvedString(ctx, t)
        val message = resolvedString(ctx, m)

        db.titleTV.text = title
        db.messageTV.text = message

        db.okayTV.setOnClickListener {
            dialog?.dismiss()
            listener.invoke()
        }

        dialog?.setCancelable(false)
        dialog?.show()
    }

}