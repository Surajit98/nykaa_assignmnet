package com.sd.nykaa_library.utils


import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sd.nykaa_library.R


object CustomBindingAdapter {


    @BindingAdapter("time")
    @JvmStatic
    fun setTime(txt: TextView, time: Long?) {
        txt.text = time.format()
    }

    @BindingAdapter("sessionTime")
    @JvmStatic
    fun setSessionTime(txt: TextView, time: Int) {
        val hour = time / 60
        var min = time
        if (hour > 0) {
            min = hour % 60
            val text = "$hour hour $min min"
            txt.text = text
        } else {
            val text = "$min minutes"
            txt.text = text
        }

    }

    @BindingAdapter("start", "end")
    @JvmStatic
    fun setSessionDuration(txt: TextView, start: Long?, end: Long?) {
        if (end == null || end == 0L) {
            txt.text = txt.context.getString(R.string.ongoing)
            return
        }
        val millis = end  - (start ?: 0L)
        txt.text = millis.formatTime()

    }


}


