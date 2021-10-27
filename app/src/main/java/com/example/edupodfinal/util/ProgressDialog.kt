package com.example.edupodfinal.util

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.example.edupodfinal.R

object ProgressDialog {

    private lateinit var dialog: Dialog

    fun show(context: Context) {

        dialog = Dialog(context, R.style.ProgressbarStyle)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.item_progress)
        dialog.setCancelable(false)
        dialog.show()

    }

    fun dismiss() {
        dialog.dismiss()
    }
}