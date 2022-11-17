package com.example.moneyexchangeapp.base

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {
    fun showLongSnackBar(message: String, action: (() -> Unit)? = null) {
        view?.let { it ->
            Snackbar.make(it,
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