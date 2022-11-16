package com.example.moneyexchangeapp.base

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment : Fragment() {
    fun showLongSnackbar(message: String, action: (() -> Unit)? = null) {
        view?.let {
            Snackbar.make(it, message, if (action==null) Snackbar.LENGTH_LONG else Snackbar.LENGTH_INDEFINITE).let {
                if (action != null)
                    it.setAction(android.R.string.ok) {
                        action()
                    }
                it
            }.show()
        }
    }
}