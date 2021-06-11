package com.sd.nykaa_library.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.sd.nykaa_library.R
import com.sd.nykaa_library.utils.ProgressHelper

abstract class BaseActivity : AppCompatActivity() {


    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        init()
    }


    abstract fun setBinding()

    abstract fun init()


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    protected fun showDialog() {
        if (dialog == null) {
            dialog = ProgressHelper.showAlert(this)
        }
        if (!dialog!!.isShowing)
            dialog?.show()
    }


    protected fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


    protected fun showSnackBar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).setBackgroundTint(
            ContextCompat.getColor(
                this,
                R.color.pink_200
            )
        ).show()
    }

    protected fun dismissDialog() {
        if (dialog != null)
            dialog?.dismiss()
    }


}