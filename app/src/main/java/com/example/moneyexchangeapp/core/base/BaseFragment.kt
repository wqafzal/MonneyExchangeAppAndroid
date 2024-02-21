package com.example.moneyexchangeapp.core.base

import android.app.AlertDialog
import android.graphics.Color
import androidx.fragment.app.Fragment
import com.example.moneyexchangeapp.R
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {

    fun showDialog(
        title: String,
        message: String,
        positiveButtonText: String,
        onPositive: (() -> Unit)? = null,
        negativeButtonText: String? = null,
        onNegative: (() -> Unit)? = null, cancelable: Boolean = true
    ) {
        val dialog = AlertDialog.Builder(requireContext()).apply {
            setIcon(R.mipmap.ic_launcher_round)
            setMessage(message)
                .setPositiveButton(
                    positiveButtonText
                ) { _, _ ->
                    onPositive?.invoke()
                }
            if (negativeButtonText != null)
                setNegativeButton(
                    negativeButtonText
                ) { _, _ ->
                    onNegative?.invoke()
                }
            setTitle(title)
            setCancelable(cancelable)
        }.show()
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED)
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE)
    }

    fun showLongSnackBar(message: String, action: (() -> Unit)? = null) {
        view?.let { it ->
            Snackbar.make(
                it,
                message,
                if (action == null) Snackbar.LENGTH_LONG else Snackbar.LENGTH_INDEFINITE
            ).let { snackBar ->
                if (action != null)
                    snackBar.setAction(android.R.string.ok) {
                        action()
                    }
                snackBar
            }.show()
        }
    }
}