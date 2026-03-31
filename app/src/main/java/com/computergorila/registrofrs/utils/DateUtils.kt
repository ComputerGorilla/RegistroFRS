package com.computergorila.registrofrs.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

object DateUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun today(): String = LocalDate.now().toString()
}