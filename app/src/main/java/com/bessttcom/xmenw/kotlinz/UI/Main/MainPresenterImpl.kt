package com.bessttcom.xmenw.kotlinz.UI.Main

import android.database.MatrixCursor
import android.provider.BaseColumns
import com.bessttcom.xmenw.kotlinz.Utils.Database.DB
import io.reactivex.disposables.Disposable


class MainPresenterImpl (val mainA: MainActivity) : MainPresenter {

    var disposable: Disposable? = null

    override fun onResume() {

        disposable = DB.userInfoSearch.subscribe {
            val c = MatrixCursor(arrayOf(BaseColumns._ID, "cityName", "image"))
            if (!it.isEmpty())
                it.forEach { c.addRow(arrayOf(1, it.email, "image")) }

            mainA.changeCursor(c)
        }
    }

    override fun onPause() {
        disposable?.dispose()
    }
}