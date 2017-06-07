package com.bessttcom.xmenw.kotlinz.UI.Main

import android.database.MatrixCursor

/**
 * Created by xmenw on 02.06.2017.
 */
interface MainActivity {
    fun changeCursor (cursor: MatrixCursor)
    fun viewPagerMessages (mess: BusMessages)
}