package com.kkh.multimodule.util

import android.annotation.SuppressLint
import android.os.Build

@SuppressLint("AnnotateVersionCheck")
fun isAndroid15OrAbove(): Boolean {
    return Build.VERSION.SDK_INT >= 35
}